package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.shared.stub.StringStub
import com.gmadorell.youtube_sync.module.youtube.domain.model.ChannelId

object ChannelIdStub {
  def create(channelId: String = StringStub.random(10)) = ChannelId(channelId)

  def random: ChannelId = create()
}
