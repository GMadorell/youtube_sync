package com.gmadorell.youtube.video

import scala.concurrent.Future

import PlaylistItemsResponseMarshaller._
import com.gmadorell.youtube.model.{PlayListId, Video, VideoId}
import com.gmadorell.youtube.shared.YoutubeRequest
import fr.hmil.roshttp.HttpRequest
import io.circe.Decoder
import io.circe.parser.decode
import monix.execution.Scheduler
import io.circe.generic.semiauto._

final class VideoSearcher(apiKey: String)(implicit scheduler: Scheduler) {
  private val requestSkeleton = YoutubeRequest.baseRequest
    .withPath("playlistItems")
    .withQueryParameters(
      "part"       -> "contentDetails",
      "key"        -> apiKey,
      "maxResults" -> "50"
    )

  def videos(playListId: PlayListId): Future[Set[Video]] = {
    val requestWithPlayListId = requestSkeleton.withQueryParameter("playlistId", playListId.id)

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
  implicit val responsePlaylistItemDecoder: Decoder[Item]               = deriveDecoder
  implicit val responsePageInfoDecoder: Decoder[PageInfo]               = deriveDecoder
  implicit val playlistsResponseDecoder: Decoder[PlaylistItemsResponse] = deriveDecoder
}
