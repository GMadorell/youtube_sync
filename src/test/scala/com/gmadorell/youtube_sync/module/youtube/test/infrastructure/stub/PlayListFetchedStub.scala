package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.youtube.application.playlist.PlayListFetched

object PlayListFetchedStub {
  def create(channelId: String = ChannelIdStub.random().id,
             playListId: String = PlayListIdStub.random().id,
             playListName: String = PlayListNameStub.random().name): PlayListFetched = {
    PlayListFetched(channelId, playListId, playListName)
  }

  def random(): PlayListFetched = create()
}
