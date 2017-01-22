package com.gmadorell

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import com.gmadorell.api.channel.ChannelId
import com.gmadorell.configuration.Configuration
import com.typesafe.config.ConfigFactory
import monix.execution.Scheduler.Implicits.global
import com.gmadorell.api.YoutubeApi
import com.gmadorell.api.playlist.PlayListId

object YoutubeSync extends App {
  val configuration = new Configuration(ConfigFactory.load())
  println(s"Your api key is: ${configuration.apiKey}")

  val api = new YoutubeApi(configuration.apiKey)

  val playlistsFuture = api.playLists(ChannelId("UCo08AK-PJuDKl5WLk3Q5VgA"))

  val result = for {
//    playLists <- api.playLists(ChannelId("UCo08AK-PJuDKl5WLk3Q5VgA"))
    videos <- api.videos(PlayListId("LLo08AK-PJuDKl5WLk3Q5VgA"))
  } yield pprint.pprintln(videos)

  Await.result(result, Duration.Inf)
}

case object Done
