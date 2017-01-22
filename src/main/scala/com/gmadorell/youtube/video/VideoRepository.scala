package com.gmadorell.youtube.video

import scala.concurrent.Future

import PlaylistItemsResponseMarshaller._
import com.gmadorell.api.playlist.PlayListId
import com.gmadorell.api.shared.YoutubeApiConstants
import fr.hmil.roshttp.{HttpRequest, Method, Protocol}
import io.circe.Decoder
import io.circe.parser.decode
import monix.execution.Scheduler
import io.circe.generic.semiauto._

trait VideoRepository {
  def videos(playListId: PlayListId): Future[Set[Video]]
}

final class RosVideoRepository(apiKey: String)(implicit scheduler: Scheduler) extends VideoRepository {
  private val requestSqueleton = HttpRequest()
    .withProtocol(Protocol.HTTPS)
    .withHost(YoutubeApiConstants.host)
    .withPath("playlistItems")
    .withQueryParameters(
      "part"       -> "contentDetails",
      "key"        -> apiKey,
      "maxResults" -> "50"
    )
    .withMethod(Method.GET)

  override def videos(playListId: PlayListId): Future[Set[Video]] = {
    val requestWithPlayListId = requestSqueleton.withQueryParameter("playlistId", playListId.id)

    def responsePlayListItemToVideo(responsePlaylistItemsItem: Item): Video =
      Video(VideoId(responsePlaylistItemsItem.contentDetails.videoId))

    def iterate(previousResponse: PlaylistItemsResponse): Future[Set[Video]] = {
      previousResponse.nextPageToken match {
        case Some(token) =>
          executePlayListItemsRequest(requestWithPlayListId.withQueryParameter("pageToken", token))
        case None => Future.successful(Set.empty)
      }
    }

    def executePlayListItemsRequest(request: HttpRequest): Future[Set[Video]] = {
      request.send().flatMap { response =>
        decode[PlaylistItemsResponse](response.body) match {
          case Right(playListItemsResponse) =>
            val responsePlayListItems = playListItemsResponse.items.map(responsePlayListItemToVideo).toSet
            iterate(playListItemsResponse).map { paginatedPlayListItems =>
              responsePlayListItems ++ paginatedPlayListItems
            }
          case Left(error) => Future.failed(error)
        }
      }
    }

    executePlayListItemsRequest(requestWithPlayListId)
  }
}

private object PlaylistItemsResponseMarshaller {
  case class PlaylistItemsResponse(kind: String,
                                   etag: String,
                                   pageInfo: PageInfo,
                                   nextPageToken: Option[String],
                                   prevPageToken: Option[String],
                                   items: Seq[Item])
  case class PageInfo(totalResults: Int, resultsPerPage: Int)
  case class Item(kind: String, etag: String, id: String, contentDetails: ItemContentDetails)
  case class ItemContentDetails(videoId: String, videoPublishedAt: Option[String])

  implicit val itemContentDetailsDecoder: Decoder[ItemContentDetails]   = deriveDecoder
  implicit val reponsePlaylistItemDecoder: Decoder[Item]                = deriveDecoder
  implicit val responsePageInfoDecoder: Decoder[PageInfo]               = deriveDecoder
  implicit val playlistsResponseDecoder: Decoder[PlaylistItemsResponse] = deriveDecoder
}
