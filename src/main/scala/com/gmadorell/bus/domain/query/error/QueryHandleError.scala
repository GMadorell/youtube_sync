package com.gmadorell.bus.domain.query.error

import com.gmadorell.bus.model.query.QueryName

sealed trait QueryHandleError

case class QueryHandlerNotFound(queryName: QueryName) extends QueryHandleError
