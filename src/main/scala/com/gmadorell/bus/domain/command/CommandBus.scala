package com.gmadorell.bus.domain.command

import scala.concurrent.Future

import com.gmadorell.bus.model.command.Command

trait CommandBus {
  def addHandler[CommandT <: Command](handler: CommandHandler[CommandT]): Unit

  def handle[CommandT <: Command](command: Command): Future[Unit]
}
