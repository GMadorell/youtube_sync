package com.gmadorell.api.playlist

import scala.concurrent.Future

import com.gmadorell.api.channel.ChannelId
import fr.hmil.roshttp.{HttpRequest, Method, Protocol}
import io.circe.Decoder
import io.circe.parser.decode
import io.circe.generic.semiauto._
import PlaylistsResponseMarshaller._
import monix.execution.Scheduler.Implicits.global

trait PlaylistRepository {
  def playlists(channelId: ChannelId): Future[Set[Playlist]]
}

final class RosPlaylistRepository(apiKey: String) extends PlaylistRepository {
  private val requestSqueleton = HttpRequest()
    .withProtocol(Protocol.HTTPS)
    .withHost("www.googleapis.com/youtube/v3/")
    .withPath("playlists")
    .withQueryParameters(
      "part" -> "id,status",
      "key"  -> apiKey
    )
    .withMethod(Method.GET)

  override def playlists(channelId: ChannelId): Future[Set[Playlist]] = {
    val firstRequest = requestSqueleton.withQueryParameter("channelId", channelId.id)

    firstRequest.send().flatMap { response =>
      decode[PlaylistsResponse](response.body) match {
        case Right(playlistsResponse) =>
          println(playlistsResponse)
          Future.successful(Set(Playlist(PlaylistId("10"))))
        case Left(error) => Future.failed(error)
      }
    }
  }
}

/*
  {
 "kind": "youtube#playlistListResponse",
 "etag": "\"gMxXHe-zinKdE9lTnzKu8vjcmDI/OXZcyEZS2KGzopMlttJsroBXnt4\"",
 "nextPageToken": "CAUQAA",
 "pageInfo": {
  "totalResults": 33,
  "resultsPerPage": 5
 },
 "items": [
  {
   "kind": "youtube#playlist",
   "etag": "\"gMxXHe-zinKdE9lTnzKu8vjcmDI/XNxej2NOImPeQP7zWezUA7KXtWY\"",
   "id": "PLSlzHiHVccM6ajxwo-8w322cKF4mJI6I_",
   "status": {
    "privacyStatus": "public"
   }
  },
  {
   "kind": "youtube#playlist",
   "etag": "\"gMxXHe-zinKdE9lTnzKu8vjcmDI/RCtWip5GsTgXMH9QIrsblEVUV_Y\"",
   "id": "PLSlzHiHVccM4VlIgo13sDwFQAyrlZQm-A",
   "status": {
    "privacyStatus": "public"
   }
  }
 ]
 */

object PlaylistsResponseMarshaller {
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
