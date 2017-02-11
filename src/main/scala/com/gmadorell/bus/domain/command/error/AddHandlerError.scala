package com.gmadorell.bus.domain.command.error

import com.gmadorell.bus.model.command.CommandName

sealed trait AddHandlerError

case class HandlerAlreadyExists(name: CommandName) extends AddHandlerError
