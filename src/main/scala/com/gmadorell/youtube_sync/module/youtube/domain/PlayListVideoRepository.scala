package com.gmadorell.youtube_sync.module.youtube.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, Video}

trait PlayListVideoRepository {
  def exists(playListId: PlayList, videoId: Video): Future[Boolean]

  def create(playListId: PlayList, videoId: Video): Future[Unit]
}
