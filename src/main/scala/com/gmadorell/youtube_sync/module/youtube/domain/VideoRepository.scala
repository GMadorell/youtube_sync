package com.gmadorell.youtube_sync.module.youtube.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, VideoId}

trait VideoRepository {
  def findVideos(playListId: PlayListId): Future[Set[VideoId]]
}
