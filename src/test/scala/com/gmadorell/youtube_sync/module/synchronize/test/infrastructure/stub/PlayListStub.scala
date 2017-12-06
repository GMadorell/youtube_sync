package com.gmadorell.youtube_sync.module.synchronize.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.shared.stub.StringStub
import com.gmadorell.youtube_sync.module.synchronize.domain.model.{PlayList, PlayListId, PlayListName}

object PlayListStub {
  def create(id: PlayListId = PlayListIdStub.create(), name: PlayListName = PlayListNameStub.create()): PlayList =
    PlayList(id, name)
}

object PlayListIdStub {
  def create(playListId: String = StringStub.random(10)): PlayListId = PlayListId(playListId)
}

object PlayListNameStub {
  def create(name: String = StringStub.random(10)): PlayListName =
    PlayListName(name)
}
