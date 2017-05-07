package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.youtube.application.playlist.PlayListFetched

final class PlayListFetchedStub {
  def create(channelId: String = ChannelIdStub.random.id,
             playListId: String = PlayListIdStub.random.id): PlayListFetched = {
    PlayListFetched(channelId, playListId)
  }

  def random: PlayListFetched = create()
}
