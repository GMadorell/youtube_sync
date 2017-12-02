package com.gmadorell.youtube_sync.module.youtube.application.sync_new

import scala.concurrent.{ExecutionContext, Future}

import cats.implicits._
import com.gmadorell.youtube_sync.module.youtube.domain.{LocalPlayListVideoRepository, RemotePlayListVideoRepository}
import com.gmadorell.youtube_sync.module.youtube.domain.error.YoutubeError
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, PlayListVideo}

final class PlayListSynchronizer(remoteRepository: RemotePlayListVideoRepository,
                                 localRepository: LocalPlayListVideoRepository)(implicit ec: ExecutionContext) {

  def synchronize(playListId: PlayListId): Future[Either[YoutubeError, Unit]] = {
    for {
      remoteVideos <- searchRemoteVideos(playListId)
      localVideos  <- searchLocalVideos(playListId)
      _            <- createMissingLocalVideos(remoteVideos, localVideos)
    } yield ().asRight[YoutubeError]
  }

  private def searchRemoteVideos(playListId: PlayListId): Future[List[PlayListVideo]] =
    remoteRepository.search(playListId)

  private def searchLocalVideos(playListId: PlayListId): Future[List[PlayListVideo]] =
    localRepository.search(playListId)

  private def createMissingLocalVideos(remoteVideos: List[PlayListVideo],
                                       localVideos: List[PlayListVideo]): Future[Unit] =
    missingLocalVideos(remoteVideos, localVideos).traverse(localRepository.create).map(_ => ())

  private def missingLocalVideos(remoteVideos: List[PlayListVideo],
                                 localVideos: List[PlayListVideo]): List[PlayListVideo] =
    remoteVideos.filter(remoteVideo => !localVideos.contains(remoteVideo))
}
