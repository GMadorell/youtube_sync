package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube.model.{ChannelId => YoutubeApiChannelId}
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{ChannelId, PlayListId}

final class ApiPlayListRepository(youtubeApiKey: String)(implicit ec: ExecutionContext) extends PlayListRepository {
  private val youtubeApi = new YoutubeApi(youtubeApiKey)

  override def findPlayLists(channelId: ChannelId): Future[Set[PlayListId]] = {
    youtubeApi.playLists(YoutubeApiChannelId(channelId.id)).map { playLists =>
      playLists.map(playList => PlayListId(playList.id.id))
    }
  }
}
