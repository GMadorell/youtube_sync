package com.gmadorell.youtube_sync.module.synchronize.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.synchronize.domain.model.{PlayList, PlayListVideo}

trait RemotePlayListVideoRepository {
  def search(playList: PlayList): Future[List[PlayListVideo]]
}
