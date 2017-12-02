package com.gmadorell.youtube_sync.module.youtube.test.behaviour

import com.gmadorell.youtube_sync.module.shared.stub.SetStub
import com.gmadorell.youtube_sync.module.youtube.application.video.FetchVideosOnPlayListFetched
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub._

final class FetchVideosOfPlayListSpec extends YoutubeBehaviourSpec {

  "A FetchVideosOnPlayListFetched" should {
    "find the videos of a playlist" in {
      val event            = PlayListFetchedStub.random()
      val videosOfPlayList = SetStub.random(() => VideoStub.random())

      val eventHandler = new FetchVideosOnPlayListFetched(videoRepository, eventBus)

      shouldFindVideosOfPlayList(PlayListIdStub.create(event.playListId), videosOfPlayList)

      videosOfPlayList.foreach { video =>
        shouldPublishEvent(
          VideoFetchedStub.create(channelId = event.channelId,
                                  playListId = event.playListId,
                                  playListName = event.playListName,
                                  videoId = video.id.id,
                                  videoName = video.name.name))
      }

      eventHandler.handle(event).futureValue
    }
  }
}
