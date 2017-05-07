package com.gmadorell.bus.infrastructure.command

import scala.concurrent.Future

import com.gmadorell.bus.domain.command.CommandBus
import com.gmadorell.bus.domain.command.error.{CommandHandleError, CommandHandlerNotFound}
import com.gmadorell.bus.model.command.Command
import com.gmadorell.youtube_sync.module.youtube.application.playlist.{FetchPlayListsOfChannelCommand, FetchPlayListsOfChannelCommandHandler}

final class RealCommandBus(fetchPlayListsOfChannelCommandHandler: FetchPlayListsOfChannelCommandHandler)
    extends CommandBus {
  def handle(command: Command): Either[CommandHandleError, Future[Unit]] = command match {
    case c: FetchPlayListsOfChannelCommand => Right(fetchPlayListsOfChannelCommandHandler.handle(c))
    case _                                 => Left(CommandHandlerNotFound(command))
  }
}
