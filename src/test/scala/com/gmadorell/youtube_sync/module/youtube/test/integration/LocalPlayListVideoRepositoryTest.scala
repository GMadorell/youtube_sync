package com.gmadorell.youtube_sync.module.youtube.test.integration

import scala.concurrent.ExecutionContext
import scala.util.Random

import cats.implicits._
import com.gmadorell.youtube_sync.infrastructure.integration.YoutubeSyncIntegrationTest
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub._

final class LocalPlayListVideoRepositoryTest extends YoutubeSyncIntegrationTest {
  "A LocalPlayListVideoRepository" should {
    "not find unexisting PlayListVideos" in runWithInjector { implicit injector =>
      localPlayListVideoRepository.search(PlayListStub.create()).futureValue should ===(List.empty)
    }

    "find existing PlayListVideos" in runWithInjector { implicit injector =>
      implicit val ec: ExecutionContext = executionContext

      val playListToVideos = configuration.test.playLists.toList.map { playListConfiguration =>
        val playList = PlayListStub.create(id = PlayListIdStub.create(playListConfiguration.playListId),
                                           name = PlayListNameStub.create(playListConfiguration.name))
        val videosOfPlayList = playListConfiguration.videos.map(videoConfig =>
          VideoStub.create(id = VideoIdStub.create(videoConfig.videoId),
                           name = VideoNameStub.create(videoConfig.videoName)))
        (playList, videosOfPlayList)
      }

      val (playList, videos) = Random.shuffle(playListToVideos).head
      val playListVideos     = videos.map(video => PlayListVideoStub.create(playList, video))

      playListVideos.toList.traverse_(localPlayListVideoRepository.create).futureValue

      localPlayListVideoRepository
        .search(playList)
        .futureValue should contain theSameElementsAs playListVideos
    }
  }
}
