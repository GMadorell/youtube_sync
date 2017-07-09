package com.gmadorell.youtube_sync.module.youtube.application.sync

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.youtube_sync.module.youtube.domain.PlayListVideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, Video}

final class VideoSynchronizer(playListVideoRepository: PlayListVideoRepository)(implicit ec: ExecutionContext) {
  def synchronize(playList: PlayList, video: Video): Future[Unit] = {
    playListVideoRepository.exists(playList, video).flatMap { isVideoExistent =>
      if (isVideoExistent) {
        Future.successful(())
      } else {
        playListVideoRepository.create(playList, video)
      }
    }
  }
}
