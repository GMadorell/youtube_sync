package com.gmadorell.youtube_sync.module.youtube.application.sync

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.application.video.VideoFetched
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, VideoId}

final class SynchronizeVideoOnVideoFetchedEventHandler(videoSynchronizer: VideoSynchronizer) {
  def handle(event: VideoFetched): Future[Unit] = {
    videoSynchronizer.synchronize(PlayListId(event.playListId), VideoId(event.videoId))
  }
}
