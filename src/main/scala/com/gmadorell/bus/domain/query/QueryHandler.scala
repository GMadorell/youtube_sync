package com.gmadorell.bus.domain.query

import scala.concurrent.Future

import com.gmadorell.bus.model.query.{Query, QueryName, Response}

trait QueryHandler[ResponseT <: Response] {
  val name: QueryName

  def handle(query: Query): Future[ResponseT]
}
