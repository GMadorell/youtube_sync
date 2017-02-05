package com.gmadorell.bus.domain.command

import scala.concurrent.Future

import com.gmadorell.bus.model.command.Command

trait CommandHandler[CommandT <: Command] {
  def handle(command: CommandT): Future[Unit]
}
