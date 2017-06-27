package com.gmadorell.youtube.model

final case class PlayList(id: PlayListId, name: PlayListName)

final case class PlayListId(id: String)

final case class PlayListName(name: String)
