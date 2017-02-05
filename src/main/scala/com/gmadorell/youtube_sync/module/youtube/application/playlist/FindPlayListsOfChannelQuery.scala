package com.gmadorell.youtube_sync.module.youtube.application.playlist

import com.gmadorell.bus.model.query.Query

case class FindPlayListsOfChannelQuery(channelId: String) extends Query
