package com.gmadorell.youtube_sync.module.youtube.application.video

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.bus.domain.event.EventBus
import com.gmadorell.youtube_sync.module.youtube.application.playlist.PlayListFetched
import com.gmadorell.youtube_sync.module.youtube.domain.VideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.PlayListId

final class FetchVideosOnPlayListFetched(repository: VideoRepository, eventBus: EventBus)(
    implicit ec: ExecutionContext) {
  def handle(event: PlayListFetched): Future[Unit] = {
    repository.findVideos(PlayListId(event.playListId)).map { videoIds =>
      videoIds.map { video =>
        eventBus.handle(
          VideoFetched(event.channelId, event.playListId, event.playListName, video.id.id, video.name.name))
      }
    }
  }
}
