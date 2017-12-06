package com.gmadorell.youtube_sync.infrastructure.acceptance

import scala.concurrent.duration._

import com.gmadorell.bus.domain.event.EventBus
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.infrastructure.dependency_injection.YoutubeSyncGuiceModule
import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.ScalaModule
import net.codingwell.scalaguice.InjectorExtensions._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures

abstract class YoutubeSyncAcceptanceTest extends AcceptanceTest {
  override def baseModule: ScalaModule = new YoutubeSyncGuiceModule

  override implicit def patienceConfig: PatienceConfig = PatienceConfig(10.seconds)

  def configuration(implicit injector: Injector): YoutubeSyncConfiguration =
    injector.instance[YoutubeSyncConfiguration]
  def eventBus(implicit injector: Injector): EventBus = injector.instance[EventBus]
}

abstract class AcceptanceTest extends WordSpec with ScalaFutures with Matchers {
  type TestResult = Unit
  type Test       = Injector => TestResult

  def baseModule: ScalaModule

  def runWithInjector(testToExecute: Test): TestResult =
    testToExecute(Guice.createInjector(baseModule))
}
