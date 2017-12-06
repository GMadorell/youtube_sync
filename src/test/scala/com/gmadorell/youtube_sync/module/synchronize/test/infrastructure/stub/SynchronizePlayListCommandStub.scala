package com.gmadorell.youtube_sync.module.synchronize.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.synchronize.application.sync.SynchronizePlayListCommand

object SynchronizePlayListCommandStub {
  def create(playListId: String = PlayListIdStub.create().id): SynchronizePlayListCommand =
    SynchronizePlayListCommand(playListId)
}
