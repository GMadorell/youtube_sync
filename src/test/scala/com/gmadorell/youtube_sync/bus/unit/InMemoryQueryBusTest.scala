package com.gmadorell.youtube_sync.bus.unit

import scala.concurrent.Future

import com.gmadorell.bus.domain.query.QueryHandler
import com.gmadorell.bus.domain.query.error.{
  AddQueryHandlerError,
  QueryHandleError,
  QueryHandlerAlreadyExists,
  QueryHandlerNotFound
}
import com.gmadorell.bus.infrastructure.query.InMemoryQueryBus
import com.gmadorell.bus.model.query.{Query, QueryName, Response}
import com.gmadorell.youtube_sync.util.matcher.EitherMatchers
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

final class InMemoryQueryBusTest extends WordSpec with Matchers with ScalaFutures with EitherMatchers {
  "An InMemoryQueryBus" should {
    "answer a query" in {
      val queryBus     = new InMemoryQueryBus()
      val queryHandler = new SimpleCountQueryHandlerStub

      queryBus.addHandler(queryHandler) shouldBe right

      queryBus.handle(SimpleQueryStub()) should beSuccessfulFuture[Response](SimpleResponseStub())
      queryHandler.amountOfProcessedQueries shouldBe 1
    }

    "fail when a handler for a query does not exist" in {
      val queryBus = new InMemoryQueryBus()

      queryBus.handle(SimpleQueryStub()) should beLeft[QueryHandleError](QueryHandlerNotFound(StubQueryNames.simple))
    }

    "fail when adding a handler twice for the same query" in {
      val queryBus     = new InMemoryQueryBus()
      val queryHandler = new SimpleCountQueryHandlerStub

      queryBus.addHandler(queryHandler) shouldBe right
      queryBus.addHandler(queryHandler) should beLeft[AddQueryHandlerError](
        QueryHandlerAlreadyExists(queryHandler.name))
    }
  }
}

private object StubQueryNames {
  val simple = QueryName("simple")
}

private case class SimpleQueryStub() extends Query {
  override val name: QueryName = StubQueryNames.simple
}

private case class SimpleResponseStub() extends Response {
  override val name: QueryName = StubQueryNames.simple
}

private class SimpleCountQueryHandlerStub extends QueryHandler {

  private var amountOfProcessedQueries_ = 0

  def amountOfProcessedQueries: Int = amountOfProcessedQueries_

  override val name: QueryName = StubQueryNames.simple

  override def handle(query: Query): Future[SimpleResponseStub] = {
    amountOfProcessedQueries_ += 1
    Future.successful(SimpleResponseStub())
  }
}
