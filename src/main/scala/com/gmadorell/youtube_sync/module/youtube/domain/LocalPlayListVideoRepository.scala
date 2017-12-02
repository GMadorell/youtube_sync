package com.gmadorell.youtube_sync.module.youtube.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, PlayListVideo}

trait LocalPlayListVideoRepository {
  def search(playListId: PlayListId): Future[List[PlayListVideo]]

  def create(video: PlayListVideo): Future[Unit]
}
