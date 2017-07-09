package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.PlayListVideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, Video}

final class FilesystemPlayListVideoRepository() extends PlayListVideoRepository {
  override def exists(playList: PlayList, video: Video): Future[Boolean] =
    Future.successful(false) // TODO

  override def create(playList: PlayList, video: Video): Future[Unit] =
    Future.successful(()) // TODO
}
