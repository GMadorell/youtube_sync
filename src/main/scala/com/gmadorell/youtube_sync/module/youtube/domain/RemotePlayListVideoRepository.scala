package com.gmadorell.youtube_sync.module.youtube.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, PlayListVideo}

trait RemotePlayListVideoRepository {
  def search(playList: PlayList): Future[List[PlayListVideo]]
}
