package com.gmadorell.youtube_sync.module.youtube.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, Video}

trait PlayListVideoRepository {
  def exists(playList: PlayList, video: Video): Future[Boolean]

  def create(playList: PlayList, video: Video): Future[Unit]
}
