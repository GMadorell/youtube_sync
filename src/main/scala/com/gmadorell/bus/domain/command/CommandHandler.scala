package com.gmadorell.bus.domain.command

import scala.concurrent.Future

import com.gmadorell.bus.model.command.{Command, CommandName}

trait CommandHandler {
  val name: CommandName

  def handle(command: Command): Future[Unit]
}
