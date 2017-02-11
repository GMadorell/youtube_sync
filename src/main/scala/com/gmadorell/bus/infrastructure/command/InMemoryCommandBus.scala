package com.gmadorell.bus.infrastructure.command

import scala.concurrent.Future

import com.gmadorell.bus.domain.command.error.{AddHandlerError, HandleError, HandlerAlreadyExists, HandlerNotFound}
import com.gmadorell.bus.domain.command.{CommandBus, CommandHandler}
import com.gmadorell.bus.model.command.{Command, CommandName}

final class InMemoryCommandBus extends CommandBus {

  private var handlers = Set.empty[CommandHandler]

  override def addHandler(handler: CommandHandler): Either[AddHandlerError, Unit] = findHandler(handler.name) match {
    case Some(_) => Left(HandlerAlreadyExists(handler.name))
    case None =>
      handlers += handler
      Right(())
  }

  override def handle(command: Command): Either[HandleError, Future[Unit]] = findHandler(command.name) match {
    case Some(commandHandler) => Right(commandHandler.handle(command))
    case None                 => Left(HandlerNotFound(command.name))
  }

  private def findHandler(name: CommandName) = handlers.find(_.name == name)
}
