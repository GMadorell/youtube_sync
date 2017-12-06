package com.gmadorell.youtube_sync.module.synchronize.application.sync

import scala.concurrent.Future

import cats.implicits._
import com.gmadorell.youtube_sync.module.synchronize.domain.error.YoutubeError
import com.gmadorell.youtube_sync.module.synchronize.domain.model.PlayListId

final class SynchronizePlayListCommandHandler(playListSynchronizer: PlayListSynchronizer) {

  def handle(command: SynchronizePlayListCommand): Future[Either[YoutubeError, Unit]] =
    validate(command) match {
      case left @ Left(_)    => Future.successful(left.map(_ => ()))
      case Right(playListId) => playListSynchronizer.synchronize(playListId)
    }

  private def validate(command: SynchronizePlayListCommand): Either[YoutubeError, PlayListId] =
    PlayListId(command.playListId).asRight[YoutubeError]

}
