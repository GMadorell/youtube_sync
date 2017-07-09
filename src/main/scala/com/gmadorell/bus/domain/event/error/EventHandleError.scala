package com.gmadorell.bus.domain.event.error

import com.gmadorell.bus.model.event.Event

trait EventHandleError

case class EventHandlerNotFound(event: Event) extends EventHandleError
