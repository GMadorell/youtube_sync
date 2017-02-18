package com.gmadorell.bus.domain.query.error

import com.gmadorell.bus.model.query.QueryName

sealed trait AddQueryHandlerError

case class QueryHandlerAlreadyExists(queryName: QueryName) extends AddQueryHandlerError
