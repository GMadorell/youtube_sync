package com.gmadorell.youtube_sync.infrastructure.actor

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.gmadorell.youtube_sync.module.synchronize.application.sync.{
  SynchronizePlayListCommand,
  SynchronizePlayListCommandHandler
}

object PlayListSynchronizerActor {
  def create(actorSystem: ActorSystem, commandHandler: SynchronizePlayListCommandHandler, playLists: Seq[String])(
      implicit ec: ExecutionContext): PlayListSynchronizerActorRef =
    PlayListSynchronizerActorRef(actorSystem.actorOf(props(commandHandler, playLists), name()))

  private def props(commandHandler: SynchronizePlayListCommandHandler, playLists: Seq[String])(
      implicit ec: ExecutionContext): Props =
    Props(new PlayListSynchronizerActor(commandHandler, playLists)(ec))

  private def name(): String = "play-list-synchronizer"

  final case class PlayListSynchronizerActorRef(ref: ActorRef)

  object Messages {
    case object SynchronizePlayLists
  }
}

final class PlayListSynchronizerActor(commandHandler: SynchronizePlayListCommandHandler, playLists: Seq[String])(
    implicit ec: ExecutionContext)
    extends Actor {

  private var playListSynchronizeProcess: Option[Future[_]] = None

  import PlayListSynchronizerActor.Messages._

  override def preStart(): Unit = self ! SynchronizePlayLists

  override def receive: Receive = {
    case SynchronizePlayLists =>
      playListSynchronizeProcess match {
        case Some(currentProcess) =>
          if (currentProcess.isCompleted) {
            playListSynchronizeProcess = Some(executeNewSynchronizeJob)
          }
        case None =>
          playListSynchronizeProcess = Some(executeNewSynchronizeJob)
      }
  }

  private def executeNewSynchronizeJob = {
    val newProcess = Future
      .sequence(playLists.map(playListId => commandHandler.handle(SynchronizePlayListCommand(playListId))))
      .map { synchResults =>
        synchResults.foreach {
          case Left(error) => println(error)
          case Right(_)    => ()
        }
      }
      .recover {
        case NonFatal(error) =>
          println(error)
          throw error
      }
    newProcess
  }
}
