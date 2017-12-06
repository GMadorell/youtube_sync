package com.gmadorell.youtube_sync.module.synchronize.test.integration

import scala.util.Random

import com.gmadorell.youtube_sync.infrastructure.integration.YoutubeSyncIntegrationTest
import com.gmadorell.youtube_sync.module.synchronize.test.infrastructure.stub._

final class RemotePlayListVideoRepositoryTest extends YoutubeSyncIntegrationTest {
  "A RemotePlayListVideoRepository" should {
    "fetch play list videos" in runWithContext { implicit context =>
      val playListToVideos = configuration.test.playLists.map { playListConfiguration =>
        val playList = PlayListStub.create(id = PlayListIdStub.create(playListConfiguration.playListId),
                                           name = PlayListNameStub.create(playListConfiguration.name))
        val videosOfPlayList = playListConfiguration.videos.map(videoConfig =>
          VideoStub.create(id = VideoIdStub.create(videoConfig.videoId),
                           name = VideoNameStub.create(videoConfig.videoName)))
        (playList, videosOfPlayList)
      }

      val (playList, videos)     = Random.shuffle(playListToVideos).head
      val expectedPlayListVideos = videos.map(video => PlayListVideoStub.create(playList, video))

      remotePlayListVideoRepository
        .search(playList)
        .futureValue should contain theSameElementsAs expectedPlayListVideos
    }
  }
}
