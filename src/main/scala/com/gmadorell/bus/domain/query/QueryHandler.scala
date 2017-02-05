package com.gmadorell.bus.domain.query

import scala.concurrent.Future

import com.gmadorell.bus.model.query.{Query, Response}

trait QueryHandler[QueryT <: Query, ResponseT <: Response] {
  def handle(query: QueryT): Future[ResponseT]
}
