package com.gmadorell.bus.domain.command.error

import com.gmadorell.bus.model.command.CommandName

sealed trait HandleError

case class HandlerNotFound(name: CommandName) extends HandleError
