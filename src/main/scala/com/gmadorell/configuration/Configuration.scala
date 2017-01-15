package com.gmadorell.configuration

import com.typesafe.config.Config

final class Configuration(private val config: Config) {
  val apiKey: String = config.getString("youtube-api.api-key")
}
