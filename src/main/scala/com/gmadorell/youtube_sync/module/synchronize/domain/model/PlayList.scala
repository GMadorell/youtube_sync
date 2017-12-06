package com.gmadorell.youtube_sync.module.synchronize.domain.model

final case class PlayList(id: PlayListId, name: PlayListName)

final case class PlayListId(id: String)

final case class PlayListName(name: String)
