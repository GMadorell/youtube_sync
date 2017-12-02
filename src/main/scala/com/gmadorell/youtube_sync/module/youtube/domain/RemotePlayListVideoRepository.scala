package com.gmadorell.youtube_sync.module.youtube.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, PlayListVideo}

trait RemotePlayListVideoRepository {
  def search(playListId: PlayListId): Future[List[PlayListVideo]]
}
