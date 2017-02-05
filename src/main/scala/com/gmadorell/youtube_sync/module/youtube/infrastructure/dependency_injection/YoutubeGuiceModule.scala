package com.gmadorell.youtube_sync.module.youtube.infrastructure.dependency_injection

import scala.concurrent.ExecutionContext

import com.gmadorell.youtube_sync.infrastructure.configuration.Configuration
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.youtube.infrastructure.ApiPlayListRepository
import net.codingwell.scalaguice.ScalaModule
import com.google.inject.{Inject, Provider, Singleton}

final class YoutubeGuiceModule extends ScalaModule {
  override def configure(): Unit = {
    bind[PlayListRepository].toProvider[ApiPlayListRepositoryProvider].in[Singleton]
  }
}

private class ApiPlayListRepositoryProvider @Inject()(configuration: Configuration)(implicit ec: ExecutionContext)
    extends Provider[PlayListRepository] {
  override def get(): PlayListRepository = new ApiPlayListRepository(configuration.apiKey)
}
