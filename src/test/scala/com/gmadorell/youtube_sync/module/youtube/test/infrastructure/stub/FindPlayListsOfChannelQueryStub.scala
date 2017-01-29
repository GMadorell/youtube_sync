package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.youtube.application.playlist.FindPlayListsOfChannelQuery

object FindPlayListsOfChannelQueryStub {
  def create(channelId: String = ChannelIdStub.random.id): FindPlayListsOfChannelQuery =
    FindPlayListsOfChannelQuery(channelId)

  def random: FindPlayListsOfChannelQuery = create()
}
