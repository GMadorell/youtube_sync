package com.gmadorell.youtube_sync.module.youtube.application.playlist

case class FindPlayListsOfChannelResponse(channelId: String, playListIds: Set[String])
