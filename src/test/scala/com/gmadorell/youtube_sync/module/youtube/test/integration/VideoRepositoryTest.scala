package com.gmadorell.youtube_sync.module.youtube.test.integration

import com.gmadorell.youtube_sync.infrastructure.configuration.Configuration
import com.gmadorell.youtube_sync.infrastructure.integration.YoutubeSyncIntegrationTest
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub.{PlayListIdStub, VideoIdStub}
import net.codingwell.scalaguice.InjectorExtensions._

final class VideoRepositoryTest extends YoutubeSyncIntegrationTest {
  "A VideoRepository" should {
    "obtain the videos of a given play list" in runWithInjector { implicit injector =>
      val configuration = injector.instance[Configuration]
      val playListId    = PlayListIdStub.create(configuration.test.testPlayList.playListId)
      val videos        = configuration.test.testPlayList.videos.map(VideoIdStub.create).toSet

      videoRepository.findVideos(playListId).futureValue should contain theSameElementsAs videos
    }

    "not obtain videos of a nonexistent play list" in runWithInjector { implicit injector =>
      videoRepository.findVideos(PlayListIdStub.random).futureValue should contain theSameElementsAs Set.empty
    }
  }
}
