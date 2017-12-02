package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.youtube.application.sync_new.SynchronizePlayListCommand

object SynchronizePlayListCommandStub {
  def create(playListId: String = PlayListIdStub.create().id): SynchronizePlayListCommand =
    SynchronizePlayListCommand(playListId)
}
