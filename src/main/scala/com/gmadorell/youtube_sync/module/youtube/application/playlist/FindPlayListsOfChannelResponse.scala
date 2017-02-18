package com.gmadorell.youtube_sync.module.youtube.application.playlist

import com.gmadorell.bus.model.query.{QueryName, Response}

case class FindPlayListsOfChannelResponse(channelId: String, playListIds: Set[String]) extends Response {
  override val name: QueryName = FindPlayListsOfChannelQuery.name
}
