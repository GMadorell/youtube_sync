package com.gmadorell.bus.infrastructure.event

import scala.concurrent.Future

import com.gmadorell.bus.domain.event.EventBus
import com.gmadorell.bus.domain.event.error.{EventHandleError, EventHandlerNotFound}
import com.gmadorell.bus.model.event.Event

final class SimpleEventBus extends EventBus {

  def handle(event: Event): Either[EventHandleError, Future[Unit]] = event match {
    case _ => Left(EventHandlerNotFound(event))
  }
}

case object UnregisteredHandlers extends EventHandleError
