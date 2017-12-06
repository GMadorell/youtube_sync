package com.gmadorell.youtube_sync.infrastructure.dependency_injection

import scala.concurrent.ExecutionContext

import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.synchronize.infrastructure.dependency_injection.SynchronizeModule
import com.typesafe.config.ConfigFactory

final class YoutubeSyncModule {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global
  val configuration                 = new YoutubeSyncConfiguration(ConfigFactory.load("application.conf"))

  val synchronizeModule = new SynchronizeModule(configuration)
}
