package com.gmadorell.youtube_sync.module.youtube.test.integration

import com.gmadorell.youtube_sync.infrastructure.integration.YoutubeSyncIntegrationTest
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub.{
  PlayListIdStub,
  VideoIdStub,
  VideoNameStub,
  VideoStub
}

final class VideoRepositoryTest extends YoutubeSyncIntegrationTest {
  "A VideoRepository" should {
    "obtain the videos of a given play list" in runWithInjector { implicit injector =>
      configuration.test.playLists.map { playListConfiguration =>
        val playListId = PlayListIdStub.create(playListConfiguration.playListId)
        val videos = playListConfiguration.videos.map(videoConfig =>
          VideoStub.create(VideoIdStub.create(videoConfig.videoId), VideoNameStub.create(videoConfig.videoName)))
        videoRepository.findVideos(playListId).futureValue should contain theSameElementsAs videos
      }
    }

    "not obtain videos of a nonexistent play list" in runWithInjector { implicit injector =>
      videoRepository.findVideos(PlayListIdStub.random).futureValue should contain theSameElementsAs Set.empty
    }
  }
}
