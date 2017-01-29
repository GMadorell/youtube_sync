package com.gmadorell.youtube_sync.module.youtube.test.stub

import com.gmadorell.youtube_sync.module.shared.stub.StringStub
import com.gmadorell.youtube_sync.module.youtube.domain.model.PlayListId

object PlayListIdStub {
  def create(playListId: String = StringStub.random(10)): PlayListId = PlayListId(playListId)

  def random: PlayListId = create()
}
