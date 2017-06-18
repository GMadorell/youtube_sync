package com.gmadorell.youtube_sync.module.youtube.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, VideoId}

trait PlayListVideoRepository {
  def exists(playListId: PlayListId, videoId: VideoId): Future[Boolean]

  def create(playListId: PlayListId, videoId: VideoId): Future[Unit]
}
