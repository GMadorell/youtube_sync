package com.gmadorell.youtube_sync.module.youtube.test.acceptance

import scala.util.Random

import com.gmadorell.youtube_sync.infrastructure.acceptance.YoutubeSyncAcceptanceTest
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.youtube.application.video.VideoFetched
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub._

final class DownloadVideosOnVideoFetchedTest extends YoutubeSyncAcceptanceTest {
  "Videos" should {
    "be downloaded on video fetched event" ignore runWithInjector { implicit injector =>
      val videoFetched = obtainRandomVideoFetchedEvent(configuration)
      val playList = PlayListStub.create(id = PlayListIdStub.create(videoFetched.playListId),
                                         name = PlayListNameStub.create(videoFetched.playListName))
      val video = VideoStub.create(id = VideoIdStub.create(videoFetched.videoId),
                                   name = VideoNameStub.create(videoFetched.videoName))

      playListVideoRepository.exists(playList, video).futureValue shouldBe false

      val handleResult = eventBus.handle(videoFetched)
      handleResult.isRight shouldBe true
      handleResult.right.get.futureValue

      playListVideoRepository.exists(playList, video).futureValue shouldBe true
    }
  }

  private def obtainRandomVideoFetchedEvent(config: YoutubeSyncConfiguration): VideoFetched = {
    val all = config.test.playLists.flatMap { playListConfiguration =>
      playListConfiguration.videos.map(
        videoConfig =>
          VideoFetchedStub.create(
            channelId = config.test.dummyChannelId,
            playListId = playListConfiguration.playListId,
            playListName = playListConfiguration.name,
            videoId = videoConfig.videoId,
            videoName = videoConfig.videoName
        ))
    }
    Random.shuffle(all).head
  }
}
