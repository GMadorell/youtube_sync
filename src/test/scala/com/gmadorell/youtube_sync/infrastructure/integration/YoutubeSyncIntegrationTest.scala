package com.gmadorell.youtube_sync.infrastructure.integration

import com.gmadorell.youtube_sync.infrastructure.dependency_injection.YoutubeSyncGuiceModule
import com.gmadorell.youtube_sync.module.youtube.domain.{PlayListRepository, VideoRepository}
import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.ScalaModule
import net.codingwell.scalaguice.InjectorExtensions._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures
import scala.concurrent.duration._

abstract class YoutubeSyncIntegrationTest extends IntegrationTest {
  override def baseModule: ScalaModule = new YoutubeSyncGuiceModule

  override implicit def patienceConfig: PatienceConfig = PatienceConfig(10.seconds)

  def playListRepository(implicit injector: Injector): PlayListRepository = injector.instance[PlayListRepository]
  def videoRepository(implicit injector: Injector): VideoRepository       = injector.instance[VideoRepository]
}

abstract class IntegrationTest extends WordSpec with ScalaFutures with Matchers {
  type TestResult = Unit
  type Test       = Injector => TestResult

  def baseModule: ScalaModule

  def runWithInjector(testToExecute: Test): TestResult =
    testToExecute(Guice.createInjector(baseModule))
}
