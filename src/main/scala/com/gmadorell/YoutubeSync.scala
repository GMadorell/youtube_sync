package com.gmadorell

object YoutubeSync extends App {

//  val injector       = Guice.createInjector(new YoutubeSyncGuiceModule)
//  val commandHandler = injector.instance[CommandBus]
//  val queryBus       = injector.instance[QueryBus]
//  val configuration  = injector.instance[Configuration]
//  println(s"Your api key is: ${configuration.apiKey}")

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
