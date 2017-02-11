package com.gmadorell.bus.domain.query

import com.gmadorell.bus.model.query.{Query, Response}

trait QueryBus {
  def addHandler[QueryT <: Query](handler: QueryHandler[_]): Unit

  def handle[QueryT <: Query](query: QueryT): Response
}
