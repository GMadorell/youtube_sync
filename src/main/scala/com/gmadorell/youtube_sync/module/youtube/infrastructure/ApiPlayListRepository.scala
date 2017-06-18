package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube.model.{ChannelId => YoutubeApiChannelId}
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{ChannelId, PlayListId}

final class ApiPlayListRepository(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext) extends PlayListRepository {
  override def findPlayLists(channelId: ChannelId): Future[Set[PlayListId]] = {
    youtubeApi.playLists(YoutubeApiChannelId(channelId.id)).map { playLists =>
      playLists.map(playList => PlayListId(playList.id.id))
    }
  }
}
