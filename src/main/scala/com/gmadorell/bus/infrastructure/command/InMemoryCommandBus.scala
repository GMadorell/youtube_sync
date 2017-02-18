package com.gmadorell.bus.infrastructure.command

import scala.concurrent.Future

import com.gmadorell.bus.domain.command.error.{AddCommandHandlerError, CommandHandleError, CommandHandlerAlreadyExists, CommandHandlerNotFound}
import com.gmadorell.bus.domain.command.{CommandBus, CommandHandler}
import com.gmadorell.bus.model.command.{Command, CommandName}

final class InMemoryCommandBus extends CommandBus {

  private var handlers = Set.empty[CommandHandler]

  override def addHandler(handler: CommandHandler): Either[AddCommandHandlerError, Unit] = findHandler(handler.name) match {
    case Some(_) => Left(CommandHandlerAlreadyExists(handler.name))
    case None =>
      handlers += handler
      Right(())
  }

  override def handle(command: Command): Either[CommandHandleError, Future[Unit]] = findHandler(command.name) match {
    case Some(commandHandler) => Right(commandHandler.handle(command))
    case None                 => Left(CommandHandlerNotFound(command.name))
  }

  private def findHandler(name: CommandName) = handlers.find(_.name == name)
}
