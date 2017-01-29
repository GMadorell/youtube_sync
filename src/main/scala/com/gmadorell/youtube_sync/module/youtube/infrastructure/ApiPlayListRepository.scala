package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{ChannelId, PlayListId}

final class ApiPlayListRepository extends PlayListRepository {
  override def findPlayLists(channelId: ChannelId): Future[Set[PlayListId]] = {
    Future.successful(Set())
  }
}
