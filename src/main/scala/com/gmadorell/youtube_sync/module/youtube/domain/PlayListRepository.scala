package com.gmadorell.youtube_sync.module.youtube.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, PlayListId}

trait PlayListRepository {
  def search(playListId: PlayListId): Future[Option[PlayList]]
}
