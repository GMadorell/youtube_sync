package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.shared.stub.StringStub
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, PlayListId, PlayListName}

object PlayListStub {
  def create(id: PlayListId = PlayListIdStub.random(), name: PlayListName = PlayListNameStub.random()): PlayList =
    PlayList(id, name)

  def random(): PlayList = create()
}

object PlayListIdStub {
  def create(playListId: String = StringStub.random(10)): PlayListId = PlayListId(playListId)

  def random(): PlayListId = create()
}

object PlayListNameStub {
  def create(name: String = StringStub.random(10)): PlayListName =
    PlayListName(name)

  def random(): PlayListName = create()
}
