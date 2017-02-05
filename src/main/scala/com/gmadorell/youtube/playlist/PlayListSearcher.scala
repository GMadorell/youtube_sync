package com.gmadorell.youtube.playlist

import scala.concurrent.Future

import PlaylistsResponseMarshaller._
import com.gmadorell.youtube.model.{ChannelId, PlayList, PlayListId}
import com.gmadorell.youtube.shared.YoutubeRequest
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.exceptions.HttpException
import io.circe.Decoder
import io.circe.generic.semiauto._
import io.circe.parser.decode
import monix.execution.Scheduler

final class PlayListSearcher(apiKey: String)(implicit scheduler: Scheduler) {
  private val requestSkeleton = YoutubeRequest.baseRequest
    .withPath("playlists")
    .withQueryParameters(
      "part"       -> "id,status",
      "key"        -> apiKey,
      "maxResults" -> "20"
    )

  def playLists(channelId: ChannelId): Future[Set[PlayList]] = {
    val requestWithChannelId = requestSkeleton.withQueryParameter("channelId", channelId.id)

    def responsePlayListItemToPlaylist(responsePlaylistItem: ResponsePlaylistItem): PlayList =
      PlayList(PlayListId(responsePlaylistItem.id))

    def iterate(previousResponse: PlaylistsResponse): Future[Set[PlayList]] = {
      previousResponse.nextPageToken match {
        case Some(token) =>
          executePlayListsRequest(requestWithChannelId.withQueryParameter("pageToken", token))
        case None => Future.successful(Set.empty)
      }
    }

    def executePlayListsRequest(request: HttpRequest): Future[Set[PlayList]] = {
      request
        .send()
        .flatMap { response =>
          decode[PlaylistsResponse](response.body) match {
            case Right(playListsResponse) =>
              val responsePlayLists = playListsResponse.items.map(responsePlayListItemToPlaylist).toSet
              iterate(playListsResponse).map { paginatedPlayLists =>
                responsePlayLists ++ paginatedPlayLists
              }
            case Left(error) => Future.failed(error)
          }
        }
        .recover {
          case HttpException(response) =>
            response.statusCode match {
              case 404 => Set()
            }
        }
    }

    executePlayListsRequest(requestWithChannelId)
  }
}

private object PlaylistsResponseMarshaller {
  case class ResponsePlaylistStatus(privacyStatus: String)
  case class ResponsePlaylistItem(kind: String, etag: String, id: String, status: ResponsePlaylistStatus)
  case class ResponsePageInfo(totalResults: Int, resultsPerPage: Int)
  case class PlaylistsResponse(kind: String,
                               etag: String,
                               pageInfo: ResponsePageInfo,
                               nextPageToken: Option[String],
                               items: Seq[ResponsePlaylistItem])

  implicit val reponsePlaylistStatusDecoder: Decoder[ResponsePlaylistStatus] = deriveDecoder
  implicit val reponsePlaylistItemDecoder: Decoder[ResponsePlaylistItem]     = deriveDecoder
  implicit val responsePageInfoDecoder: Decoder[ResponsePageInfo]            = deriveDecoder
  implicit val playlistsResponseDecoder: Decoder[PlaylistsResponse]          = deriveDecoder
}
