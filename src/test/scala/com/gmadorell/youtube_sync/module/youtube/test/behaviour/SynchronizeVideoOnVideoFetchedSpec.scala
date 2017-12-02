package com.gmadorell.youtube_sync.module.youtube.test.behaviour

import com.gmadorell.youtube_sync.module.youtube.application.sync.{SynchronizeVideoOnVideoFetchedEventHandler, VideoSynchronizer}
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub._

final class SynchronizeVideoOnVideoFetchedSpec extends YoutubeBehaviourSpec {
  val handler = new SynchronizeVideoOnVideoFetchedEventHandler(new VideoSynchronizer(playListVideoRepository))

  "SynchronizeVideoOnVideoFetchedEventHandler" should {
    "create a video pertaining to a playlist if it didn't already exist" in {
      val videoFetched = VideoFetchedStub.random()

      val playList = PlayListStub.create(id = PlayListIdStub.create(videoFetched.playListId),
                                         name = PlayListNameStub.create(videoFetched.playListName))
      val video = VideoStub.create(id = VideoIdStub.create(videoFetched.videoId),
                                   name = VideoNameStub.create(videoFetched.videoName))

      playListVideoShouldNotExist(playList, video)
      shouldCreatePlayListVideo(playList, video)

      handler.handle(videoFetched).futureValue
    }

    "not create a video pertaining to a playlist if it already exists" in {
      val videoFetched = VideoFetchedStub.random()

      val playList = PlayListStub.create(id = PlayListIdStub.create(videoFetched.playListId),
                                         name = PlayListNameStub.create(videoFetched.playListName))
      val video = VideoStub.create(id = VideoIdStub.create(videoFetched.videoId),
                                   name = VideoNameStub.create(videoFetched.videoName))

      playListVideoShouldExist(playList, video)

      handler.handle(videoFetched).futureValue
    }
  }
}
