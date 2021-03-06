package com.gmadorell.youtube_sync.module.synchronize.test.integration

import com.gmadorell.youtube_sync.infrastructure.integration.YoutubeSyncIntegrationTest
import com.gmadorell.youtube_sync.module.synchronize.test.infrastructure.stub._

final class PlayListRepositoryTest extends YoutubeSyncIntegrationTest {
  "A PlayListRepository" should {
    "find existing playlist" in runWithContext { implicit context =>
      val playList = configuration.test.playLists
        .map(testPlayList =>
          PlayListStub.create(PlayListIdStub.create(testPlayList.playListId),
                              PlayListNameStub.create(testPlayList.name)))
        .head

      playListRepository.search(playList.id).futureValue should ===(Some(playList))
    }

    "not find unexisting playlist" in runWithContext { implicit context =>
      val playListId = PlayListIdStub.create()

      playListRepository.search(playListId).futureValue should ===(None)
    }
  }
}
