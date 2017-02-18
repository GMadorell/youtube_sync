package com.gmadorell.bus.domain.command.error

import com.gmadorell.bus.model.command.CommandName

sealed trait AddCommandHandlerError

case class CommandHandlerAlreadyExists(name: CommandName) extends AddCommandHandlerError
