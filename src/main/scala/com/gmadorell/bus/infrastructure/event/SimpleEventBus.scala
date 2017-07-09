package com.gmadorell.bus.infrastructure.event

import scala.concurrent.Future

import com.gmadorell.bus.domain.event.EventBus
import com.gmadorell.bus.domain.event.error.{EventHandleError, EventHandlerNotFound}
import com.gmadorell.bus.model.event.Event
import com.gmadorell.youtube_sync.module.youtube.application.playlist.PlayListFetched
import com.gmadorell.youtube_sync.module.youtube.application.sync.SynchronizeVideoOnVideoFetchedEventHandler
import com.gmadorell.youtube_sync.module.youtube.application.video.{FetchVideosOnPlayListFetched, VideoFetched}

final class SimpleEventBus extends EventBus {

  private var fetchVideosOnPlayListFetched: Option[FetchVideosOnPlayListFetched]                             = None
  private var synchronizeVideoOnVideoFetchedEventHandler: Option[SynchronizeVideoOnVideoFetchedEventHandler] = None

  def handle(event: Event): Either[EventHandleError, Future[Unit]] = event match {
    case playListFetched: PlayListFetched =>
      fetchVideosOnPlayListFetched.fold[Either[EventHandleError, Future[Unit]]](Left(UnregisteredHandlers))(handler =>
        Right(handler.handle(playListFetched)))
    case videoFetched: VideoFetched =>
      synchronizeVideoOnVideoFetchedEventHandler.fold[Either[EventHandleError, Future[Unit]]](
        Left(UnregisteredHandlers))(handler => Right(handler.handle(videoFetched)))
    case _ => Left(EventHandlerNotFound(event))
  }

  def registerHandlers(
      fetchVideosOnPlayListFetched: FetchVideosOnPlayListFetched,
      synchronizeVideoOnVideoFetchedEventHandler: SynchronizeVideoOnVideoFetchedEventHandler): Unit = {
    this.fetchVideosOnPlayListFetched = Some(fetchVideosOnPlayListFetched)
    this.synchronizeVideoOnVideoFetchedEventHandler = Some(synchronizeVideoOnVideoFetchedEventHandler)
  }
}

case object UnregisteredHandlers extends EventHandleError
