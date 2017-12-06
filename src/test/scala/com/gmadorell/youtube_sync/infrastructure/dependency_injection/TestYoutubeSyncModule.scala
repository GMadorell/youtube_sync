package com.gmadorell.youtube_sync.infrastructure.dependency_injection

import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.typesafe.config.ConfigFactory

final class TestYoutubeSyncModule extends YoutubeSyncModule {
  override val configuration: YoutubeSyncConfiguration = new YoutubeSyncConfiguration(ConfigFactory.load("test.conf"))
}
