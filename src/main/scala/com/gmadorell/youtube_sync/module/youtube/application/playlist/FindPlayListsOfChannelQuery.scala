package com.gmadorell.youtube_sync.module.youtube.application.playlist

import com.gmadorell.bus.model.query.{Query, QueryName}

case class FindPlayListsOfChannelQuery(channelId: String) extends Query {
  override val name: QueryName = FindPlayListsOfChannelQuery.name
}

object FindPlayListsOfChannelQuery {
  val name = QueryName("find_playlists_of_channel")
}
