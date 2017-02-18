package com.gmadorell.youtube_sync.bus.unit

import scala.concurrent.Future

import com.gmadorell.bus.domain.command.CommandHandler
import com.gmadorell.bus.domain.command.error.{AddHandlerError, HandleError, HandlerAlreadyExists, HandlerNotFound}
import com.gmadorell.bus.infrastructure.command.InMemoryCommandBus
import com.gmadorell.bus.model.command.{Command, CommandName}
import com.gmadorell.youtube_sync.util.matcher.EitherMatchers
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures

final class InMemoryCommandBusTest extends WordSpec with Matchers with ScalaFutures with EitherMatchers {
  "An InMemoryCommandBus" should {
    "handle a command" in {
      val commandBus = new InMemoryCommandBus
      val handler    = new SimpleCountCommandHandlerStub

      commandBus.addHandler(handler)

      commandBus.handle(SimpleCommandStub()) shouldBe successfulFuture
      handler.amountOfProcessedCommands shouldBe 1
    }

    "fail when adding two handlers for the same command name" in {
      val commandBus = new InMemoryCommandBus
      val handler    = new SimpleCountCommandHandlerStub

      commandBus.addHandler(handler) shouldBe right
      commandBus.addHandler(handler) should beLeft[AddHandlerError](HandlerAlreadyExists(handler.name))
    }

    "fail when a handler for a certain command doesn't exist" in {
      val commandBus = new InMemoryCommandBus

      commandBus.handle(SimpleCommandStub()) should beLeft[HandleError](HandlerNotFound(StubCommandNames.simple))
    }
  }
}

private object StubCommandNames {
  val simple = CommandName("simple")
}

private case class SimpleCommandStub() extends Command {
  override val name: CommandName = StubCommandNames.simple
}

private class SimpleCountCommandHandlerStub extends CommandHandler {

  private var amountOfProcessedCommands_ = 0

  def amountOfProcessedCommands: Int = amountOfProcessedCommands_

  override val name: CommandName = StubCommandNames.simple

  override def handle(command: Command): Future[Unit] = {
    amountOfProcessedCommands_ += 1
    Future.successful(())
  }
}
