package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.youtube.application.video.VideoFetched

object VideoFetchedStub {
  def create(channelId: String = ChannelIdStub.random().id,
             playListId: String = PlayListIdStub.random().id,
             videoId: String = VideoIdStub.random().id): VideoFetched = {
    VideoFetched(channelId, playListId, videoId)
  }

  def random(): VideoFetched = create()
}
