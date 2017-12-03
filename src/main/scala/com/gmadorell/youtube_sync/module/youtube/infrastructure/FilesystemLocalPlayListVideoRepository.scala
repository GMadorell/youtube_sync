package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.Future

import com.gmadorell.youtube.model.{PlayListName, VideoName}
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.youtube.domain.LocalPlayListVideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, PlayListId, PlayListVideo}
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

final class FilesystemLocalPlayListVideoRepository(config: YoutubeSyncConfiguration)
    extends LocalPlayListVideoRepository
    with PlayListVideoCodec {

  override def search(playList: PlayList): Future[List[PlayListVideo]] =
    ???
//    playListDatabase()
//      .get(playListId)
//      .fold(Future.successful(List.empty[PlayListVideo]))(playListName =>
//        Future.successful(List.empty[PlayListVideo]) // TODO
//      )

  override def create(video: PlayListVideo): Future[Unit] = ???

//  private def playListDirectory(playListN)
//
//  private def playListDatabase(): Map[PlayListId, PlayListName] =
//    if (localDatabasePath.exists) {
//      decode[Map[PlayListId, PlayListName]](localDatabasePath.contentAsString).right.get
//    } else {
//      Map.empty
//    }
}

trait PlayListVideoCodec {
  implicit val playListIdDecoder: Decoder[PlayListId] = deriveDecoder[PlayListId]
  implicit val playListIdEncoder: Encoder[PlayListId] = deriveEncoder[PlayListId]

  implicit val playListNameDecoder: Decoder[PlayListName] = deriveDecoder[PlayListName]
  implicit val playListNameEncoder: Encoder[PlayListName] = deriveEncoder[PlayListName]

  implicit val videoNameDecoder: Decoder[VideoName] = deriveDecoder[VideoName]
  implicit val videoNameEncoder: Encoder[VideoName] = deriveEncoder[VideoName]
}
