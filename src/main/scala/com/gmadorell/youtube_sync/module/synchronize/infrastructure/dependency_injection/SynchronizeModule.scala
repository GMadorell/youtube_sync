package com.gmadorell.youtube_sync.module.synchronize.infrastructure.dependency_injection

import scala.concurrent.ExecutionContext

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
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
  val youtubeApi = new YoutubeApi(configuration.apiKey)

  val playListRepository: PlayListRepository                       = new ApiPlayListRepository(youtubeApi)
  val remotePlayListVideoRepository: RemotePlayListVideoRepository = new ApiRemotePlayListVideoRepository(youtubeApi)
  val localPlayListVideoRepository: LocalPlayListVideoRepository =
    new FilesystemLocalPlayListVideoRepository(configuration)
}
