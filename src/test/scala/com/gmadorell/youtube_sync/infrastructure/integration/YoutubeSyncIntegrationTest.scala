package com.gmadorell.youtube_sync.infrastructure.integration

import com.gmadorell.youtube_sync.infrastructure.dependency_injection.YoutubeSyncGuiceModule
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.ScalaModule
import net.codingwell.scalaguice.InjectorExtensions._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures

abstract class YoutubeSyncIntegrationTest extends IntegrationTest {
  override def baseModule: ScalaModule = new YoutubeSyncGuiceModule

  def playListRepository(implicit injector: Injector): PlayListRepository = injector.instance[PlayListRepository]
}

abstract class IntegrationTest extends WordSpec with ScalaFutures with Matchers {
  type TestResult = Unit
  type Test       = Injector => TestResult

  def baseModule: ScalaModule

  def runWithInjector(testToExecute: Test): TestResult =
    testToExecute(Guice.createInjector(baseModule))
}
