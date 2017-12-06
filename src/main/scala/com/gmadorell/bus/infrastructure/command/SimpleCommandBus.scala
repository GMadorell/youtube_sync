package com.gmadorell.bus.infrastructure.command

import scala.concurrent.Future

import com.gmadorell.bus.domain.command.CommandBus
import com.gmadorell.bus.domain.command.error.{CommandHandleError, CommandHandlerNotFound}
import com.gmadorell.bus.model.command.Command

final class SimpleCommandBus extends CommandBus {
  def handle(command: Command): Either[CommandHandleError, Future[Unit]] = command match {
    case _ => Left(CommandHandlerNotFound(command))
  }
}
