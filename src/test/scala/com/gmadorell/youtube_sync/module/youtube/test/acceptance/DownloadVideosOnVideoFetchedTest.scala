package com.gmadorell.youtube_sync.module.youtube.test.acceptance

import com.gmadorell.youtube_sync.infrastructure.acceptance.YoutubeSyncAcceptanceTest
import com.gmadorell.youtube_sync.module.youtube.application.video.VideoFetched
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub._
import com.google.inject.Injector

final class DownloadVideosOnVideoFetchedTest extends YoutubeSyncAcceptanceTest {
  "Videos" should {
    "be downloaded on video fetched event" in runWithInjector { implicit injector =>
      val videoFetched = obtainRandomVideoFetchedEventFromConfiguration()
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

  private def obtainRandomVideoFetchedEventFromConfiguration()(implicit injector: Injector): VideoFetched = {
    configuration.test.playLists.flatMap { playListConfiguration =>
      playListConfiguration.videos.map(
        videoConfig =>
          VideoFetchedStub.create(
            channelId = configuration.test.dummyChannelId,
            playListId = playListConfiguration.playListId,
            playListName = playListConfiguration.name,
            videoId = videoConfig.videoId,
            videoName = videoConfig.videoName
        ))
    }
  }.head
}
