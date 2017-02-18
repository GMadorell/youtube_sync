package com.gmadorell.bus.infrastructure.query

import scala.concurrent.Future

import com.gmadorell.bus.domain.query.error.{
  AddQueryHandlerError,
  QueryHandleError,
  QueryHandlerAlreadyExists,
  QueryHandlerNotFound
}
import com.gmadorell.bus.domain.query.{QueryBus, QueryHandler}
import com.gmadorell.bus.model.query.{Query, QueryName, Response}

final class InMemoryQueryBus extends QueryBus {

  private var handlers = Set.empty[QueryHandler]

  override def addHandler(handler: QueryHandler): Either[AddQueryHandlerError, Unit] =
    findQueryHandler(handler.name) match {
      case Some(_) => Left(QueryHandlerAlreadyExists(handler.name))
      case None =>
        handlers = handlers + handler
        Right(())
    }

  override def handle[QueryT <: Query](query: QueryT): Either[QueryHandleError, Future[Response]] =
    findQueryHandler(query.name) match {
      case Some(handler) => Right(handler.handle(query))
      case None          => Left(QueryHandlerNotFound(query.name))
    }

  private def findQueryHandler(queryName: QueryName): Option[QueryHandler] = handlers.find(_.name == queryName)
}
