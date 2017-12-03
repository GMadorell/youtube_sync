package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.Future

import better.files._
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.youtube.domain.LocalPlayListVideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

final class FilesystemLocalPlayListVideoRepository(config: YoutubeSyncConfiguration)
    extends LocalPlayListVideoRepository
    with PlayListVideoCodec {

  /*
   * Assumption:
   *   Videos will be stored in path:
   *     - root / playListName / videoName__videoId.mp3
   */

  override def search(playList: PlayList): Future[List[PlayListVideo]] = {
    val directory = playListDirectory(playList.name)
    val foundPlayListVideos = if (directory.exists) {
      directory.children
        .filter(_.isRegularFile)
        .map(file => PlayListVideo(playList, deconstructFileName(file.name)))
        .toList
    } else {
      List.empty
    }
    Future.successful(foundPlayListVideos)
  }

  override def create(video: PlayListVideo): Future[Unit] = ???

  private def playListDirectory(playListName: PlayListName): File =
    config.contentRootPath / playListName.name

  private def deconstructFileName(fileName: String): Video =
    fileName.split("__") match {
      case Array(name, idAndExtension) => {
        idAndExtension.split(".") match {
          case Array(id, _) =>
            Video(VideoId(id), VideoName(name))
        }
      }
    }
}

trait PlayListVideoCodec {
  implicit val playListIdDecoder: Decoder[PlayListId] = deriveDecoder[PlayListId]
  implicit val playListIdEncoder: Encoder[PlayListId] = deriveEncoder[PlayListId]

  implicit val playListNameDecoder: Decoder[PlayListName] = deriveDecoder[PlayListName]
  implicit val playListNameEncoder: Encoder[PlayListName] = deriveEncoder[PlayListName]

  implicit val videoNameDecoder: Decoder[VideoName] = deriveDecoder[VideoName]
  implicit val videoNameEncoder: Encoder[VideoName] = deriveEncoder[VideoName]
}
