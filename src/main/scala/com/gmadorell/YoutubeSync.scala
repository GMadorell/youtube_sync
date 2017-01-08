package com.gmadorell

import com.typesafe.config.ConfigFactory

object YoutubeSync extends App {
  println("Hello world")
  val conf = ConfigFactory.load()
  println(s"Your api key is: ${conf.getString("youtube-api.api-key")}")
}
