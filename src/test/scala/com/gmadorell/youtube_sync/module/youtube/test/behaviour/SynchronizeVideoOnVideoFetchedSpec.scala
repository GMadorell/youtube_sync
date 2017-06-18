package com.gmadorell.youtube_sync.module.youtube.test.behaviour

import com.gmadorell.youtube_sync.module.youtube.application.sync.{
  SynchronizeVideoOnVideoFetchedEventHandler,
  VideoSynchronizer
}
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub.{
  PlayListIdStub,
  VideoFetchedStub,
  VideoIdStub
}

final class SynchronizeVideoOnVideoFetchedSpec extends YoutubeBehaviourSpec {
  private implicit val ec = scala.concurrent.ExecutionContext.global
  val handler             = new SynchronizeVideoOnVideoFetchedEventHandler(new VideoSynchronizer(playListVideoRepository))

  "SynchronizeVideoOnVideoFetchedEventHandler" should {
    "create a video pertaining to a playlist if it didn't already exist" in {
      val videoFetched = VideoFetchedStub.random

      val playListId = PlayListIdStub.create(videoFetched.playListId)
      val videoId    = VideoIdStub.create(videoFetched.videoId)

      playListVideoShouldNotExist(playListId, videoId)
      shouldCreatePlayListVideo(playListId, videoId)

      handler.handle(videoFetched).futureValue
    }

    "not create a video pertaining to a playlist if it already exists" in {
      val videoFetched = VideoFetchedStub.random

      val playListId = PlayListIdStub.create(videoFetched.playListId)
      val videoId    = VideoIdStub.create(videoFetched.videoId)

      playListVideoShouldExist(playListId, videoId)

      handler.handle(videoFetched).futureValue
    }
  }
}
