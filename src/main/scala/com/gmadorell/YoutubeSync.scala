package com.gmadorell

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import com.gmadorell.bus.domain.command.CommandBus
import com.gmadorell.bus.domain.query.QueryBus
import com.gmadorell.bus.model.query.Query
import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube.model.{ChannelId, PlayListId}
import com.gmadorell.youtube_sync.infrastructure.configuration.Configuration
import com.gmadorell.youtube_sync.infrastructure.dependency_injection.YoutubeSyncGuiceModule
import com.google.inject.Guice
import com.typesafe.config.ConfigFactory
import net.codingwell.scalaguice.InjectorExtensions._

object YoutubeSync extends App {

  val injector       = Guice.createInjector(new YoutubeSyncGuiceModule)
  val commandHandler = injector.instance[CommandBus]
  val queryBus       = injector.instance[QueryBus]
  val configuration  = injector.instance[Configuration]
  println(s"Your api key is: ${configuration.apiKey}")

//  val api = new YoutubeApi(configuration.apiKey)
//
//  val playlistsFuture = api.playLists(ChannelId("UCo08AK-PJuDKl5WLk3Q5VgA"))
//
//  val result = for {
////    playLists <- api.playLists(ChannelId("UCo08AK-PJuDKl5WLk3Q5VgA"))
//    videos <- api.videos(PlayListId("LLo08AK-PJuDKl5WLk3Q5VgA"))
//  } yield pprint.pprintln(videos)
//
//  Await.result(result, Duration.Inf)
}

case object Done
