package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.Future

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListVideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, Video}

final class ApiPlayListVideoRepository(youtubeApi: YoutubeApi) extends PlayListVideoRepository {
  override def exists(playListId: PlayList, videoId: Video): Future[Boolean] =
    Future.successful(false) // TODO

  override def create(playListId: PlayList, videoId: Video): Future[Unit] =
    Future.successful(()) // TODO
}
