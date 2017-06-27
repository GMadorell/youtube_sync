package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.youtube.application.video.VideoFetched

object VideoFetchedStub {
  def create(channelId: String = ChannelIdStub.random().id,
             playListId: String = PlayListIdStub.random().id,
             playListName: String = PlayListNameStub.random().name,
             videoId: String = VideoIdStub.random().id): VideoFetched = {
    VideoFetched(channelId, playListId, playListName, videoId)
  }

  def random(): VideoFetched = create()
}
