package com.gmadorell.youtube_sync.module.synchronize.domain.error

import com.gmadorell.youtube_sync.module.synchronize.domain.model.PlayListId

sealed trait YoutubeError

final case class PlayListNotFound(playListId: PlayListId) extends YoutubeError
