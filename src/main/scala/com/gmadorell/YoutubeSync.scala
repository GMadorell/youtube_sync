package com.gmadorell

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube.model.{ChannelId, PlayListId}
import com.gmadorell.youtube_sync.infrastructure.configuration.Configuration
import com.typesafe.config.ConfigFactory
import monix.execution.Scheduler.Implicits.global

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
