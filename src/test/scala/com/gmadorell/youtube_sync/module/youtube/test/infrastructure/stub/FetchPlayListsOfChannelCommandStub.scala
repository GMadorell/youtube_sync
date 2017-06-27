package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.youtube.application.playlist.FetchPlayListsOfChannelCommand

object FetchPlayListsOfChannelCommandStub {
  def create(channelId: String = ChannelIdStub.random().id): FetchPlayListsOfChannelCommand =
    FetchPlayListsOfChannelCommand(channelId)

  def random(): FetchPlayListsOfChannelCommand = create()
}
