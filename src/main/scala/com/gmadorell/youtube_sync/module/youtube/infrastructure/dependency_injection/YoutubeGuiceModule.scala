package com.gmadorell.youtube_sync.module.youtube.infrastructure.dependency_injection

import scala.concurrent.ExecutionContext

import com.gmadorell.bus.domain.event.EventBus
import com.gmadorell.bus.infrastructure.event.SimpleEventBus
import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.youtube.application.sync.{
  SynchronizeVideoOnVideoFetchedEventHandler,
  VideoSynchronizer
}
import com.gmadorell.youtube_sync.module.youtube.application.video.FetchVideosOnPlayListFetched
import com.gmadorell.youtube_sync.module.youtube.domain.{
  PlayListRepository,
  PlayListVideoRepository,
  RemotePlayListVideoRepository,
  VideoRepository
}
import com.gmadorell.youtube_sync.module.youtube.infrastructure.{
  ApiPlayListRepository,
  ApiRemotePlayListVideoRepository,
  ApiVideoRepository,
  FilesystemPlayListVideoRepository
}
import com.google.inject.{Inject, Provider, Singleton}
import net.codingwell.scalaguice.ScalaModule

final class YoutubeGuiceModule extends ScalaModule {
  override def configure(): Unit = {
    bind[EventBus].toProvider[SimpleEventBusProvider].in[Singleton]
    bind[PlayListRepository].toProvider[ApiPlayListRepositoryProvider].in[Singleton]
    bind[VideoRepository].toProvider[ApiVideoRepositoryProvider].in[Singleton]
    bind[PlayListVideoRepository].toProvider[FilesystemPlayListVideoRepositoryProvider].in[Singleton]
    bind[YoutubeApi].toProvider[YoutubeApiProvider].in[Singleton]

    bind[RemotePlayListVideoRepository].toProvider[RemotePlayListVideoRepositoryProvider].in[Singleton]
  }
}

private class SimpleEventBusProvider @Inject()(
    videoRepository: VideoRepository,
    playListVideoRepository: PlayListVideoRepository)(implicit ec: ExecutionContext)
    extends Provider[EventBus] {
  override def get(): EventBus = {
    val eventBus = new SimpleEventBus()

    val fetchVideosOnPlayListFetched = new FetchVideosOnPlayListFetched(videoRepository, eventBus)
    val synchronizeVideoOnVideoFetchedEventHandler = new SynchronizeVideoOnVideoFetchedEventHandler(
      new VideoSynchronizer(playListVideoRepository))

    eventBus.registerHandlers(fetchVideosOnPlayListFetched, synchronizeVideoOnVideoFetchedEventHandler)
    eventBus
  }
}

private class ApiPlayListRepositoryProvider @Inject()(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext)
    extends Provider[PlayListRepository] {
  override def get(): PlayListRepository = new ApiPlayListRepository(youtubeApi)
}

private class ApiVideoRepositoryProvider @Inject()(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext)
    extends Provider[VideoRepository] {
  override def get(): VideoRepository = new ApiVideoRepository(youtubeApi)
}

private class FilesystemPlayListVideoRepositoryProvider @Inject()(configuration: YoutubeSyncConfiguration)
    extends Provider[PlayListVideoRepository] {
  override def get(): PlayListVideoRepository = new FilesystemPlayListVideoRepository(configuration.contentRootPath)
}

private class YoutubeApiProvider @Inject()(configuration: YoutubeSyncConfiguration)(implicit ec: ExecutionContext)
    extends Provider[YoutubeApi] {
  override def get(): YoutubeApi = new YoutubeApi(configuration.apiKey)
}

private class RemotePlayListVideoRepositoryProvider @Inject()(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext)
    extends Provider[RemotePlayListVideoRepository] {
  override def get(): RemotePlayListVideoRepository = new ApiRemotePlayListVideoRepository(youtubeApi)
}
