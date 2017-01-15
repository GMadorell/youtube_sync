package com.gmadorell

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import com.gmadorell.api.channel.ChannelId
import com.gmadorell.api.playlist.RosPlaylistRepository
import com.gmadorell.configuration.Configuration
import com.typesafe.config.ConfigFactory
import scala.concurrent.ExecutionContext.Implicits.global

object YoutubeSync extends App {
  val configuration = new Configuration(ConfigFactory.load())
  println(s"Your api key is: ${configuration.apiKey}")

  val playlistRepository = new RosPlaylistRepository(configuration.apiKey)

  val playlistsFuture = playlistRepository.playlists(ChannelId("UCo08AK-PJuDKl5WLk3Q5VgA"))
  Await.result(playlistsFuture.map { playlists =>
    playlists
  }, Duration.Inf)
}
