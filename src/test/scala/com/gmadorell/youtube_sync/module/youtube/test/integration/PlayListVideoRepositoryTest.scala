package com.gmadorell.youtube_sync.module.youtube.test.integration

import scala.util.Random

import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.infrastructure.integration.YoutubeSyncIntegrationTest
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, Video}
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub._

final class PlayListVideoRepositoryTest extends YoutubeSyncIntegrationTest {
  "A PlayListVideoRepository" should {
    "tell that a non-created playlist video does not exist" ignore runWithInjector { implicit injector =>
      val playList = PlayListStub.random()
      val video    = VideoStub.random()

      playListVideoRepository.exists(playList, video).futureValue shouldBe true
    }

    "tell that a created playlist video does exist" ignore runWithInjector { implicit injector =>
      val (playList, video) = obtainRandomPlayListAndVideo(configuration)
      playListVideoRepository.create(playList, video).futureValue
      playListVideoRepository.exists(playList, video).futureValue shouldBe true
    }
  }

  private def obtainRandomPlayListAndVideo(config: YoutubeSyncConfiguration): (PlayList, Video) = {
    val all = config.test.playLists.flatMap { playListConfiguration =>
      playListConfiguration.videos.map(
        videoConfig =>
          (PlayListStub.create(id = PlayListIdStub.create(playListConfiguration.playListId),
                               name = PlayListNameStub.create(playListConfiguration.name)),
           VideoStub.create(id = VideoIdStub.create(videoConfig.videoId),
                            name = VideoNameStub.create(videoConfig.videoName)))
      )
    }
    Random.shuffle(all).head
  }
}
