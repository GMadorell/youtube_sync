package com.gmadorell.bus.domain

import scala.concurrent.Future

import com.gmadorell.bus.model.{Query, Response}

trait QueryHandler[QueryT <: Query, ResponseT <: Response] {
  def handle(query: QueryT): Future[ResponseT]
}
