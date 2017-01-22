package com.gmadorell.youtube_sync.infrastructure.configuration

import com.typesafe.config.Config
import scala.collection.JavaConverters._

final class Configuration(private val config: Config) {
  val apiKey: String          = config.getString("youtube-api.api-key")
  val channels: Seq[String]   = config.getStringList("context.channels").asScala
  val playLists: Seq[String]  = config.getStringList("context.playlists").asScala
  val contentRootPath: String = config.getString("file-system.content-root-path")
}
