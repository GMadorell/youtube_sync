package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.shared.stub.SetStub
import com.gmadorell.youtube_sync.module.youtube.application.playlist.FindPlayListsOfChannelResponse

object FindPlayListsOfChannelResponseStub {
  def create(channelId: String = ChannelIdStub.random.id,
             playListIds: Set[String] = SetStub.random(PlayListIdStub.random.id)): FindPlayListsOfChannelResponse =
    FindPlayListsOfChannelResponse(channelId, playListIds)
}
