package com.gmadorell.bus.domain.query

import scala.concurrent.Future

import com.gmadorell.bus.domain.query.error.{AddQueryHandlerError, QueryHandleError}
import com.gmadorell.bus.model.query.{Query, Response}

trait QueryBus {
  def addHandler(handler: QueryHandler): Either[AddQueryHandlerError, Unit]

  def handle[QueryT <: Query](query: QueryT): Either[QueryHandleError, Future[Response]]
}
