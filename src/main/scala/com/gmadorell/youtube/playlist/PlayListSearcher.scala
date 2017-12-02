package com.gmadorell.youtube.playlist

import scala.concurrent.Future

import com.gmadorell.youtube.model.{ChannelId, PlayList, PlayListId, PlayListName}
import PlaylistsResponseMarshaller._
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
      "part"       -> "id,status,snippet",
      "key"        -> apiKey,
      "maxResults" -> "20"
    )

  def playListsByChannel(channelId: ChannelId): Future[Set[PlayList]] = {
    val requestWithChannelId = requestSkeleton.withQueryParameter("channelId", channelId.id)

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

  def playListById(playListId: PlayListId): Future[Option[PlayList]] = {
    val requestWithPlayListId = requestSkeleton.withQueryParameter("id", playListId.id)

    requestWithPlayListId
      .send()
      .flatMap { response =>
        decode[PlaylistsResponse](response.body) match {
          case Right(playListsResponse) =>
            val responsePlayLists = playListsResponse.items.map(responsePlayListItemToPlaylist).toSet
            Future.successful(Some(responsePlayLists.head))
          case Left(error) => Future.failed(error)
        }
      }
      .recover {
        case HttpException(response) =>
          response.statusCode match {
            case 404 => None
          }
      }
  }

  private def responsePlayListItemToPlaylist(responsePlaylistItem: ResponsePlaylistItem): PlayList =
    PlayList(id = PlayListId(responsePlaylistItem.id), name = PlayListName(responsePlaylistItem.snippet.title))
}

private object PlaylistsResponseMarshaller {
  case class ResponsePlayListSnippet(title: String)
  case class ResponsePlaylistStatus(privacyStatus: String)
  case class ResponsePlaylistItem(kind: String,
                                  etag: String,
                                  id: String,
                                  status: ResponsePlaylistStatus,
                                  snippet: ResponsePlayListSnippet)
  case class ResponsePageInfo(totalResults: Int, resultsPerPage: Int)
  case class PlaylistsResponse(kind: String,
                               etag: String,
                               pageInfo: ResponsePageInfo,
                               nextPageToken: Option[String],
                               items: Seq[ResponsePlaylistItem])

  implicit val responsePlayListSnippetDecoder: Decoder[ResponsePlayListSnippet] = deriveDecoder
  implicit val responsePlaylistStatusDecoder: Decoder[ResponsePlaylistStatus]   = deriveDecoder
  implicit val responsePlaylistItemDecoder: Decoder[ResponsePlaylistItem]       = deriveDecoder
  implicit val responsePageInfoDecoder: Decoder[ResponsePageInfo]               = deriveDecoder
  implicit val playlistsResponseDecoder: Decoder[PlaylistsResponse]             = deriveDecoder
}
