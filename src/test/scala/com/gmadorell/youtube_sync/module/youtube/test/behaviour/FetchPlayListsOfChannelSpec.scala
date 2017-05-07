package com.gmadorell.youtube_sync.module.youtube.test.behaviour

import com.gmadorell.youtube_sync.module.shared.stub.SetStub
import com.gmadorell.youtube_sync.module.youtube.application.playlist.{
  FetchPlayListsOfChannelCommandHandler,
  PlayListFetched
}
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub.{
  ChannelIdStub,
  FetchPlayListsOfChannelCommandStub,
  PlayListIdStub
}

final class FetchPlayListsOfChannelSpec extends YoutubeBehaviourSpec {

  private implicit val ec = scala.concurrent.ExecutionContext.global

  "A FetchPlayListsOfChannelCommandHandler" should {
    "find the playlists of a channel" in {
      val command            = FetchPlayListsOfChannelCommandStub.random
      val playListsOfChannel = SetStub.random(PlayListIdStub.random)

      val commandHandler = new FetchPlayListsOfChannelCommandHandler(playListRepository, eventBus)

      shouldFindPlayListsOfChannel(ChannelIdStub.create(command.channelId), playListsOfChannel)

      playListsOfChannel.foreach { playListId =>
        shouldPublishEvent(PlayListFetched(command.channelId, playListId.id))
      }

      commandHandler.handle(command).futureValue
    }
  }
}
