package com.gmadorell.bus.domain.event

import scala.concurrent.Future

import com.gmadorell.bus.domain.event.error.EventHandleError
import com.gmadorell.bus.model.event.Event

trait EventBus {
  def handle(event: Event): Either[EventHandleError, Future[Unit]]
}
