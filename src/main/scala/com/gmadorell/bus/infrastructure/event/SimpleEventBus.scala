package com.gmadorell.bus.infrastructure.event

import scala.concurrent.Future

import com.gmadorell.bus.domain.event.EventBus
import com.gmadorell.bus.domain.event.error.{EventHandleError, EventHandlerNotFound}
import com.gmadorell.bus.model.event.Event
import com.gmadorell.youtube_sync.module.youtube.application.playlist.PlayListFetched
import com.gmadorell.youtube_sync.module.youtube.application.sync.SynchronizeVideoOnVideoFetchedEventHandler
import com.gmadorell.youtube_sync.module.youtube.application.video.{FetchVideosOnPlayListFetched, VideoFetched}

final class SimpleEventBus(fetchVideosOnPlayListFetched: FetchVideosOnPlayListFetched,
                         synchronizeVideoOnVideoFetched: SynchronizeVideoOnVideoFetchedEventHandler)
    extends EventBus {
  def handle(event: Event): Either[EventHandleError, Future[Unit]] = event match {
    case playListFetched: PlayListFetched => Right(fetchVideosOnPlayListFetched.handle(playListFetched))
    case videoFetched: VideoFetched       => Right(synchronizeVideoOnVideoFetched.handle(videoFetched))
    case _                                => Left(EventHandlerNotFound(event))
  }
}
