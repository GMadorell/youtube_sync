package com.gmadorell.bus.domain.command

import scala.concurrent.Future

import com.gmadorell.bus.domain.command.error.{AddCommandHandlerError, CommandHandleError}
import com.gmadorell.bus.model.command.Command

trait CommandBus {
  def addHandler(handler: CommandHandler): Either[AddCommandHandlerError, Unit]

  def handle(command: Command): Either[CommandHandleError, Future[Unit]]
}
