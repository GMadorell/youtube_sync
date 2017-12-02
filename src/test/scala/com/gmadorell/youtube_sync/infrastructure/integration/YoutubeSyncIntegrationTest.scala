package com.gmadorell.youtube_sync.infrastructure.integration

import scala.concurrent.duration._

import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.infrastructure.dependency_injection.YoutubeSyncGuiceModule
import com.gmadorell.youtube_sync.module.youtube.domain.{PlayListRepository, PlayListVideoRepository, RemotePlayListVideoRepository, VideoRepository}
import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.ScalaModule
import net.codingwell.scalaguice.InjectorExtensions._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures

abstract class YoutubeSyncIntegrationTest extends IntegrationTest {
  override def baseModule: ScalaModule = new YoutubeSyncGuiceModule

  override implicit def patienceConfig: PatienceConfig = PatienceConfig(10.seconds)

  def configuration(implicit injector: Injector): YoutubeSyncConfiguration =
    injector.instance[YoutubeSyncConfiguration]
  def playListRepository(implicit injector: Injector): PlayListRepository = injector.instance[PlayListRepository]
  def videoRepository(implicit injector: Injector): VideoRepository       = injector.instance[VideoRepository]
  def playListVideoRepository(implicit injector: Injector): PlayListVideoRepository =
    injector.instance[PlayListVideoRepository]

  def remotePlayListVideoRepository(implicit injector: Injector): RemotePlayListVideoRepository =
    injector.instance[RemotePlayListVideoRepository]
}

abstract class IntegrationTest extends WordSpec with ScalaFutures with Matchers {
  type TestResult = Unit
  type Test       = Injector => TestResult

  def baseModule: ScalaModule

  def runWithInjector(testToExecute: Test): TestResult =
    testToExecute(Guice.createInjector(baseModule))
}
