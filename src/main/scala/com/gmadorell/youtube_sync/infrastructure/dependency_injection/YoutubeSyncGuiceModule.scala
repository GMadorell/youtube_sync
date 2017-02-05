package com.gmadorell.youtube_sync.infrastructure.dependency_injection

import scala.concurrent.ExecutionContext

import com.gmadorell.youtube_sync.infrastructure.configuration.Configuration
import com.gmadorell.youtube_sync.module.youtube.infrastructure.dependency_injection.YoutubeGuiceModule
import com.typesafe.config.ConfigFactory
import net.codingwell.scalaguice.ScalaModule

final class YoutubeSyncGuiceModule extends ScalaModule {
  override def configure(): Unit = {
    bind[Configuration].toInstance(new Configuration(ConfigFactory.load("application.conf")))
    bind[ExecutionContext].toInstance(scala.concurrent.ExecutionContext.global)
    install(new YoutubeGuiceModule)
  }
}