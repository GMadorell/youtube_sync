package com.gmadorell.youtube_sync.module.youtube.application.playlist

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.bus.domain.event.EventBus
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.ChannelId

final class FetchPlayListsOfChannelCommandHandler(repository: PlayListRepository, eventBus: EventBus)(
    implicit ec: ExecutionContext) {
  def handle(command: FetchPlayListsOfChannelCommand): Future[Unit] = {
    repository.findPlayLists(ChannelId(command.channelId)).map { playListIds =>
      playListIds.map { playListId =>
        eventBus.handle(PlayListFetched(command.channelId, playListId.id))
      }
    }
  }
}
