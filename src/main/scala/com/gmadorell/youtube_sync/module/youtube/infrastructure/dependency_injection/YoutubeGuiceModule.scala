package com.gmadorell.youtube_sync.module.youtube.infrastructure.dependency_injection

import scala.concurrent.ExecutionContext

import com.gmadorell.bus.domain.event.EventBus
import com.gmadorell.bus.infrastructure.event.SimpleEventBus
import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.youtube.domain.{PlayListRepository, RemotePlayListVideoRepository}
import com.gmadorell.youtube_sync.module.youtube.infrastructure.{
  ApiPlayListRepository,
  ApiRemotePlayListVideoRepository
}
import com.google.inject.{Inject, Provider, Singleton}
import net.codingwell.scalaguice.ScalaModule

final class YoutubeGuiceModule extends ScalaModule {
  override def configure(): Unit = {
    bind[EventBus].toProvider[SimpleEventBusProvider].in[Singleton]
    bind[YoutubeApi].toProvider[YoutubeApiProvider].in[Singleton]

    bind[RemotePlayListVideoRepository].toProvider[RemotePlayListVideoRepositoryProvider].in[Singleton]
    bind[PlayListRepository].toProvider[PlayListRepositoryProvider].in[Singleton]
  }
}

private class SimpleEventBusProvider @Inject()()(implicit ec: ExecutionContext) extends Provider[EventBus] {
  override def get(): EventBus = {
    new SimpleEventBus() // TODO is this needed?
  }
}

private class YoutubeApiProvider @Inject()(configuration: YoutubeSyncConfiguration)(implicit ec: ExecutionContext)
    extends Provider[YoutubeApi] {
  override def get(): YoutubeApi = new YoutubeApi(configuration.apiKey)
}

private class RemotePlayListVideoRepositoryProvider @Inject()(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext)
    extends Provider[RemotePlayListVideoRepository] {
  override def get(): RemotePlayListVideoRepository = new ApiRemotePlayListVideoRepository(youtubeApi)
}

private class PlayListRepositoryProvider @Inject()(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext)
    extends Provider[PlayListRepository] {
  override def get(): PlayListRepository = new ApiPlayListRepository(youtubeApi)
}
