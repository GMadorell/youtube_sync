package com.gmadorell.youtube_sync.infrastructure.integration

import scala.concurrent.ExecutionContext

import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.infrastructure.dependency_injection.YoutubeSyncGuiceModule
import com.gmadorell.youtube_sync.module.youtube.domain.{
  LocalPlayListVideoRepository,
  PlayListRepository,
  RemotePlayListVideoRepository
}
import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.ScalaModule
import net.codingwell.scalaguice.InjectorExtensions._
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}

abstract class YoutubeSyncIntegrationTest extends IntegrationTest {
  override def baseModule: ScalaModule = new YoutubeSyncGuiceModule

  def configuration(implicit injector: Injector): YoutubeSyncConfiguration =
    injector.instance[YoutubeSyncConfiguration]

  def executionContext(implicit injector: Injector): ExecutionContext =
    injector.instance[ExecutionContext]
  def playListRepository(implicit injector: Injector): PlayListRepository =
    injector.instance[PlayListRepository]
  def remotePlayListVideoRepository(implicit injector: Injector): RemotePlayListVideoRepository =
    injector.instance[RemotePlayListVideoRepository]
  def localPlayListVideoRepository(implicit injector: Injector): LocalPlayListVideoRepository =
    injector.instance[LocalPlayListVideoRepository]
}

abstract class IntegrationTest
    extends WordSpec
    with ScalaFutures
    with Matchers
    with TypeCheckedTripleEquals
    with IntegrationPatience {
  type TestResult = Unit
  type Test       = Injector => TestResult

  def baseModule: ScalaModule

  def runWithInjector(testToExecute: Test): TestResult =
    testToExecute(Guice.createInjector(baseModule))
}
