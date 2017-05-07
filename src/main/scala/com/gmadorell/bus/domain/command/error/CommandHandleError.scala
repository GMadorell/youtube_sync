package com.gmadorell.bus.domain.command.error

import com.gmadorell.bus.model.command.Command

sealed trait CommandHandleError

case class CommandHandlerNotFound(command: Command) extends CommandHandleError
