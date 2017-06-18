package com.gmadorell.youtube_sync.module.youtube.application.sync

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.youtube_sync.module.youtube.domain.PlayListVideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, VideoId}

final class VideoSynchronizer(playListVideoRepository: PlayListVideoRepository)(implicit ec: ExecutionContext) {
  def synchronize(playListId: PlayListId, videoId: VideoId): Future[Unit] = {
    playListVideoRepository.exists(playListId, videoId).flatMap { isVideoExistent =>
      if (isVideoExistent) {
        Future.successful(())
      } else {
        playListVideoRepository.create(playListId, videoId)
      }
    }
  }
}
