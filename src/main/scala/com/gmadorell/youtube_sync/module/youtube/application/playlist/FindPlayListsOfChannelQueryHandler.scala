package com.gmadorell.youtube_sync.module.youtube.application.playlist

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.bus.domain.query.QueryHandler
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.ChannelId

final class FindPlayListsOfChannelQueryHandler(repository: PlayListRepository)(implicit ec: ExecutionContext)
    extends QueryHandler[FindPlayListsOfChannelQuery, FindPlayListsOfChannelResponse] {
  override def handle(query: FindPlayListsOfChannelQuery): Future[FindPlayListsOfChannelResponse] = {
    repository.findPlayLists(ChannelId(query.channelId)).map { playListIds =>
      FindPlayListsOfChannelResponse(query.channelId, playListIds.map(_.id))
    }
  }
}
