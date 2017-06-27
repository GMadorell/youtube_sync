package com.gmadorell.youtube_sync.module.youtube.application.playlist

import com.gmadorell.bus.model.event.Event

final case class PlayListFetched(channelId: String, playListId: String, playListName: String) extends Event
