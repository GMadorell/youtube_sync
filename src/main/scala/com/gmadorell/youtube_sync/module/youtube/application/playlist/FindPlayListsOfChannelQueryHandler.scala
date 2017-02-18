package com.gmadorell.youtube_sync.module.youtube.application.playlist

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.bus.domain.query.QueryHandler
import com.gmadorell.bus.model.query.{Query, QueryName}
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.ChannelId

final class FindPlayListsOfChannelQueryHandler(repository: PlayListRepository)(implicit ec: ExecutionContext)
    extends QueryHandler {

  override val name: QueryName = FindPlayListsOfChannelQuery.name

  override def handle(query: Query): Future[FindPlayListsOfChannelResponse] = {
    // TODO implement query system so that that you can pass directly an instance of FindPlayListsOfChannelQuery
    val castedQuery = query.asInstanceOf[FindPlayListsOfChannelQuery] // FIXME this shouldn't happen
    handle(castedQuery)
  }

  private def handle(query: FindPlayListsOfChannelQuery): Future[FindPlayListsOfChannelResponse] = {
    repository.findPlayLists(ChannelId(query.channelId)).map { playListIds =>
      FindPlayListsOfChannelResponse(query.channelId, playListIds.map(_.id))
    }
  }
}
