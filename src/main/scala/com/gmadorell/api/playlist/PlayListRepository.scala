package com.gmadorell.api.playlist

import scala.concurrent.Future

import com.gmadorell.api.channel.ChannelId
import fr.hmil.roshttp.{HttpRequest, Method, Protocol}
import io.circe.Decoder
import io.circe.parser.decode
import io.circe.generic.semiauto._
import PlaylistsResponseMarshaller._
import com.gmadorell.api.shared.YoutubeApiConstants
import monix.execution.Scheduler

trait PlayListRepository {
  def playLists(channelId: ChannelId): Future[Set[PlayList]]
}

final class RosPlayListRepository(apiKey: String)(implicit scheduler: Scheduler) extends PlayListRepository {
  private val requestSqueleton = HttpRequest()
    .withProtocol(Protocol.HTTPS)
    .withHost(YoutubeApiConstants.host)
    .withPath("playlists")
    .withQueryParameters(
      "part"       -> "id,status",
      "key"        -> apiKey,
      "maxResults" -> "20"
    )
    .withMethod(Method.GET)

  override def playLists(channelId: ChannelId): Future[Set[PlayList]] = {
    val requestWithChannelId = requestSqueleton.withQueryParameter("channelId", channelId.id)

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
      request.send().flatMap { response =>
        decode[PlaylistsResponse](response.body) match {
          case Right(playListsResponse) =>
            val responsePlayLists = playListsResponse.items.map(responsePlayListItemToPlaylist).toSet
            iterate(playListsResponse).map { paginatedPlayLists =>
              responsePlayLists ++ paginatedPlayLists
            }
          case Left(error) => Future.failed(error)
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
