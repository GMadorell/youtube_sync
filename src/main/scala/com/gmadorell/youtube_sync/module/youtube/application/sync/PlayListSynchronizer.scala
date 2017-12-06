package com.gmadorell.youtube_sync.module.youtube.application.sync

import scala.concurrent.{ExecutionContext, Future}

import cats.data.EitherT
import cats.implicits._
import com.gmadorell.youtube_sync.module.youtube.domain.{
  LocalPlayListVideoRepository,
  PlayListRepository,
  RemotePlayListVideoRepository
}
import com.gmadorell.youtube_sync.module.youtube.domain.error.{PlayListNotFound, YoutubeError}
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, PlayListId, PlayListVideo}

final class PlayListSynchronizer(playListRepository: PlayListRepository,
                                 remoteRepository: RemotePlayListVideoRepository,
                                 localRepository: LocalPlayListVideoRepository)(implicit ec: ExecutionContext) {

  private type Result[T] = EitherT[Future, YoutubeError, T]

  def synchronize(playListId: PlayListId): Future[Either[YoutubeError, Unit]] = {
    synchronizeT(playListId).value
  }

  private def synchronizeT(playListId: PlayListId): Result[Unit] = {
    for {
      playList     <- findPlayList(playListId)
      remoteVideos <- searchRemoteVideos(playList)
      localVideos  <- searchLocalVideos(playList)
      _            <- createMissingLocalVideos(remoteVideos, localVideos)
    } yield ().asRight[YoutubeError]
  }

  private def findPlayList(playListId: PlayListId): Result[PlayList] = {
    EitherT.fromOptionF(playListRepository.search(playListId), PlayListNotFound(playListId))
  }

  private def searchRemoteVideos(playList: PlayList): Result[List[PlayListVideo]] =
    EitherT.liftF(remoteRepository.search(playList))

  private def searchLocalVideos(playList: PlayList): Result[List[PlayListVideo]] =
    EitherT.liftF(localRepository.search(playList))

  private def createMissingLocalVideos(remoteVideos: List[PlayListVideo],
                                       localVideos: List[PlayListVideo]): Result[Unit] =
    EitherT.liftF(missingLocalVideos(remoteVideos, localVideos).traverse(localRepository.create).map(_ => ()))

  private def missingLocalVideos(remoteVideos: List[PlayListVideo],
                                 localVideos: List[PlayListVideo]): List[PlayListVideo] =
    remoteVideos.filter(remoteVideo => !localVideos.contains(remoteVideo))
}
