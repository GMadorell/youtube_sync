package com.gmadorell.bus.domain.command.error

import com.gmadorell.bus.model.command.CommandName

sealed trait CommandHandleError

case class CommandHandlerNotFound(name: CommandName) extends CommandHandleError
