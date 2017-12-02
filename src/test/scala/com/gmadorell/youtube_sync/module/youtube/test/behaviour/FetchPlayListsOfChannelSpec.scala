package com.gmadorell.youtube_sync.module.youtube.test.behaviour

import com.gmadorell.youtube_sync.module.shared.stub.SetStub
import com.gmadorell.youtube_sync.module.youtube.application.playlist.{
  FetchPlayListsOfChannelCommandHandler,
  PlayListFetched
}
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub.{
  ChannelIdStub,
  FetchPlayListsOfChannelCommandStub,
  PlayListStub
}

final class FetchPlayListsOfChannelSpec extends YoutubeBehaviourSpec {

  "A FetchPlayListsOfChannelCommandHandler" should {
    "find the playlists of a channel" in {
      val command            = FetchPlayListsOfChannelCommandStub.random()
      val playListsOfChannel = SetStub.random(() => PlayListStub.random())

      val commandHandler = new FetchPlayListsOfChannelCommandHandler(playListRepository, eventBus)

      shouldFindPlayListsOfChannel(ChannelIdStub.create(command.channelId), playListsOfChannel)

      playListsOfChannel.foreach { playList =>
        shouldPublishEvent(PlayListFetched(command.channelId, playList.id.id, playList.name.name))
      }

      commandHandler.handle(command).futureValue
    }
  }
}
