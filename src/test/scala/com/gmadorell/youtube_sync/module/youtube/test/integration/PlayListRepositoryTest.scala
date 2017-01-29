package com.gmadorell.youtube_sync.module.youtube.test.integration

import com.gmadorell.youtube_sync.infrastructure.configuration.Configuration
import com.gmadorell.youtube_sync.infrastructure.integration.YoutubeSyncIntegrationTest
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub.{ChannelIdStub, PlayListIdStub}
import net.codingwell.scalaguice.InjectorExtensions._

final class PlayListRepositoryTest extends YoutubeSyncIntegrationTest {
  "A PlayListRepository" should {
    "obtain the play lists of a given channelId" in runWithInjector { implicit injector =>
      val configuration = injector.instance[Configuration]
      val channelId     = ChannelIdStub.create(configuration.test.dummyChannelId)
      val playLists     = configuration.test.playListsOfDummyChannel.map(PlayListIdStub.create).toSet

      playListRepository.findPlayLists(channelId).futureValue shouldBe playLists
    }

    "not find any playlist in an invented channel" in runWithInjector { implicit injector =>
      playListRepository.findPlayLists(ChannelIdStub.random).futureValue shouldBe Set()
    }
  }
}
