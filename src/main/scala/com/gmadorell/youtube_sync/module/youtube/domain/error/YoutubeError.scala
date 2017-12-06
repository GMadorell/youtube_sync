package com.gmadorell.youtube_sync.module.youtube.domain.error

import com.gmadorell.youtube_sync.module.youtube.domain.model.PlayListId

sealed trait YoutubeError

final case class PlayListNotFound(playListId: PlayListId) extends YoutubeError
