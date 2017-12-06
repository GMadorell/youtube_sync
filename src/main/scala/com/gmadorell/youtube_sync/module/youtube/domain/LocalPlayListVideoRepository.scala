package com.gmadorell.youtube_sync.module.youtube.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, PlayListVideo}

trait LocalPlayListVideoRepository {
  def search(playList: PlayList): Future[List[PlayListVideo]]

  def create(video: PlayListVideo): Future[Unit]
}
