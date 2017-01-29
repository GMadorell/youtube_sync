package com.gmadorell.youtube_sync.module.youtube.domain

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.model.{ChannelId, PlayListId}

trait PlayListRepository {
  def findPlayLists(channelId: ChannelId): Future[Set[PlayListId]]
}
