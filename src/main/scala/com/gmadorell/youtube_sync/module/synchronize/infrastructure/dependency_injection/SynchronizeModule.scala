package com.gmadorell.youtube_sync.module.synchronize.infrastructure.dependency_injection

import scala.concurrent.ExecutionContext

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.synchronize.application.sync.{
  PlayListSynchronizer,
  SynchronizePlayListCommandHandler
}
import com.gmadorell.youtube_sync.module.synchronize.domain.{
  LocalPlayListVideoRepository,
  PlayListRepository,
  RemotePlayListVideoRepository
}
import com.gmadorell.youtube_sync.module.synchronize.infrastructure.{
  ApiPlayListRepository,
  ApiRemotePlayListVideoRepository,
  FilesystemLocalPlayListVideoRepository
}

final class SynchronizeModule(configuration: YoutubeSyncConfiguration)(implicit ec: ExecutionContext) {
  lazy val youtubeApi = new YoutubeApi(configuration.apiKey)

  lazy val playListRepository: PlayListRepository = new ApiPlayListRepository(youtubeApi)
  lazy val remotePlayListVideoRepository: RemotePlayListVideoRepository =
    new ApiRemotePlayListVideoRepository(youtubeApi)
  lazy val localPlayListVideoRepository: LocalPlayListVideoRepository =
    new FilesystemLocalPlayListVideoRepository(configuration)

  // Command Handlers
  lazy val synchronizePlayListCommandHandler = new SynchronizePlayListCommandHandler(
    new PlayListSynchronizer(playListRepository, remotePlayListVideoRepository, localPlayListVideoRepository))
}
