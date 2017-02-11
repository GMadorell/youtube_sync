package com.gmadorell.bus.domain.command

import scala.concurrent.Future

import com.gmadorell.bus.domain.command.error.{AddHandlerError, HandleError}
import com.gmadorell.bus.model.command.Command

trait CommandBus {
  def addHandler(handler: CommandHandler): Either[AddHandlerError, Unit]

  def handle(command: Command): Either[HandleError, Future[Unit]]
}
