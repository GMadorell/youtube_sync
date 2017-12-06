package com.gmadorell.youtube_sync.module.synchronize.test.acceptance

import com.gmadorell.youtube_sync.infrastructure.acceptance.YoutubeSyncAcceptanceTest
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.synchronize.domain.model._
import com.gmadorell.youtube_sync.module.synchronize.test.infrastructure.stub._

final class SynchronizeYoutubePlaylistsTest extends YoutubeSyncAcceptanceTest {
  "Youtube playlists" should {
    "be synchronized" in runWithContext { implicit context =>
      context.setupActors()

      val expected: Map[PlayList, Seq[PlayListVideo]] =
        expectedPlayListVideosToBeCreated(configuration)

      eventually {
        expected.foreach {
          case (playList, playListVideos) =>
            localPlayListVideoRepository
              .search(playList)
              .futureValue should contain theSameElementsAs playListVideos
        }
      }
    }
  }

  private def expectedPlayListVideosToBeCreated(
      configuration: YoutubeSyncConfiguration): Map[PlayList, Seq[PlayListVideo]] = {
    val playListToVideos = configuration.test.playLists.map { playListConfiguration =>
      val playList = PlayListStub.create(id = PlayListIdStub.create(playListConfiguration.playListId),
                                         name = PlayListNameStub.create(playListConfiguration.name))
      val videosOfPlayList = playListConfiguration.videos.map(
        videoConfig =>
          VideoStub.create(id = VideoIdStub.create(videoConfig.videoId),
                           name = VideoNameStub.create(videoConfig.videoName)))
      (playList, videosOfPlayList)
    }.toMap

    val playListToPlayListVideos = playListToVideos.map {
      case (playList, videos) => (playList, videos.map(video => PlayListVideoStub.create(playList, video)))
    }
    playListToPlayListVideos
  }
}
