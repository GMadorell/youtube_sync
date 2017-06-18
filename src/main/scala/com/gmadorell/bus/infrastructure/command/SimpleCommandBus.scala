package com.gmadorell.bus.infrastructure.command

import scala.concurrent.Future

import com.gmadorell.bus.domain.command.CommandBus
import com.gmadorell.bus.domain.command.error.{CommandHandleError, CommandHandlerNotFound}
import com.gmadorell.bus.model.command.Command
import com.gmadorell.youtube_sync.module.youtube.application.playlist.{
  FetchPlayListsOfChannelCommand,
  FetchPlayListsOfChannelCommandHandler
}

final class SimpleCommandBus(fetchPlayListsOfChannelCommandHandler: FetchPlayListsOfChannelCommandHandler)
    extends CommandBus {
  def handle(command: Command): Either[CommandHandleError, Future[Unit]] = command match {
    case fetchPlayListsOfChannelCommand: FetchPlayListsOfChannelCommand =>
      Right(fetchPlayListsOfChannelCommandHandler.handle(fetchPlayListsOfChannelCommand))
    case _ => Left(CommandHandlerNotFound(command))
  }
}
