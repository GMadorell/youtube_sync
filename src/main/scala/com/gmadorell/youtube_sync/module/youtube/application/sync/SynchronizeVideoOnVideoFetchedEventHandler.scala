package com.gmadorell.youtube_sync.module.youtube.application.sync

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.application.video.VideoFetched
import com.gmadorell.youtube_sync.module.youtube.domain.model._

final class SynchronizeVideoOnVideoFetchedEventHandler(videoSynchronizer: VideoSynchronizer) {
  def handle(event: VideoFetched): Future[Unit] = {
    videoSynchronizer.synchronize(PlayList(PlayListId(event.playListId), PlayListName(event.playListName)),
                                  Video(VideoId(event.videoId), VideoName(event.videoName)))
  }
}
