package com.gmadorell.youtube_sync.module.youtube.infrastructure.dependency_injection

import scala.concurrent.ExecutionContext

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.youtube.domain.{PlayListRepository, VideoRepository}
import com.gmadorell.youtube_sync.module.youtube.infrastructure.{ApiPlayListRepository, ApiVideoRepository}
import net.codingwell.scalaguice.ScalaModule
import com.google.inject.{Inject, Provider, Singleton}

final class YoutubeGuiceModule extends ScalaModule {
  override def configure(): Unit = {
    bind[YoutubeApi].toProvider[YoutubeApiProvider].in[Singleton]
    bind[PlayListRepository].toProvider[ApiPlayListRepositoryProvider].in[Singleton]
    bind[VideoRepository].toProvider[ApiVideoRepositoryProvider].in[Singleton]
  }
}

private class YoutubeApiProvider @Inject()(configuration: YoutubeSyncConfiguration)(implicit ec: ExecutionContext)
    extends Provider[YoutubeApi] {
  override def get(): YoutubeApi = new YoutubeApi(configuration.apiKey)
}

private class ApiPlayListRepositoryProvider @Inject()(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext)
    extends Provider[PlayListRepository] {
  override def get(): PlayListRepository = new ApiPlayListRepository(youtubeApi)
}

private class ApiVideoRepositoryProvider @Inject()(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext)
    extends Provider[VideoRepository] {
  override def get(): VideoRepository = new ApiVideoRepository(youtubeApi)
}
