package com.gmadorell.youtube_sync.bus.unit

import scala.concurrent.Future

import com.gmadorell.bus.domain.command.CommandHandler
import com.gmadorell.bus.infrastructure.command.InMemoryCommandBus
import com.gmadorell.bus.model.command.{Command, CommandName}
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures

final class InMemoryCommandBusTest extends WordSpec with Matchers with ScalaFutures {
  "An InMemoryCommandBus" should {
    "handle a command" in {
      val commandBus = new InMemoryCommandBus
      val handler    = new SimpleCountCommandHandlerStub

      commandBus.addHandler(handler)

      val handleResult = commandBus.handle(SimpleCommandStub())
      handleResult.isRight shouldBe true
      handleResult.right.get.futureValue shouldBe (())
      handler.amountOfProcessedCommands shouldBe 1
    }

    "fail when adding two handlers for the same command name" in {}

    "fail when a handler for a certain command doesn't exist" in {}
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
