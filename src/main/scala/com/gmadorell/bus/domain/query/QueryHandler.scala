package com.gmadorell.bus.domain.query

import scala.concurrent.Future

import com.gmadorell.bus.model.query.{Query, QueryName, Response}

trait QueryHandler {
  val name: QueryName

  def handle(query: Query): Future[Response]
}
