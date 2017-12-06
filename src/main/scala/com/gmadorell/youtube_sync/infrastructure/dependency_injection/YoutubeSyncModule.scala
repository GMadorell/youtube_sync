package com.gmadorell.youtube_sync.infrastructure.dependency_injection

import scala.concurrent.ExecutionContext

import akka.actor.ActorSystem
import com.gmadorell.youtube_sync.infrastructure.actor.PlayListSynchronizerActor
import com.gmadorell.youtube_sync.infrastructure.actor.PlayListSynchronizerActor.Messages.SynchronizePlayLists
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.synchronize.infrastructure.dependency_injection.SynchronizeModule
import com.markatta.akron.{CronExpression, CronTab}
import com.typesafe.config.ConfigFactory

abstract class YoutubeSyncModule {
  val configuration: YoutubeSyncConfiguration

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global
  lazy val actorSystem              = ActorSystem.apply("youtube-sync")

  lazy val synchronizeModule = new SynchronizeModule(configuration)

  def setupActors(): Unit = {
    val playListSynchronizerActor =
      PlayListSynchronizerActor.create(actorSystem,
                                       synchronizeModule.synchronizePlayListCommandHandler,
                                       configuration.playLists)

    val crontab = actorSystem.actorOf(CronTab.props, "crontab")

    crontab ! CronTab.Schedule(playListSynchronizerActor.ref, SynchronizePlayLists, CronExpression("*/30 * * * *"))
  }
}

final class ProductionYoutubeSyncModule extends YoutubeSyncModule {
  override val configuration = new YoutubeSyncConfiguration(ConfigFactory.load("application.conf"))
}
