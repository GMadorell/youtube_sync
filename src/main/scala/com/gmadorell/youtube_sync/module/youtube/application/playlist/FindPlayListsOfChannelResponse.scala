package com.gmadorell.youtube_sync.module.youtube.application.playlist

import com.gmadorell.bus.model.Response

case class FindPlayListsOfChannelResponse(channelId: String, playListIds: Set[String]) extends Response
