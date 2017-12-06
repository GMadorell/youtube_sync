package com.gmadorell.youtube_sync.infrastructure.integration

import scala.concurrent.ExecutionContext

import better.files._
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.infrastructure.dependency_injection.{TestYoutubeSyncModule, YoutubeSyncModule}
import com.gmadorell.youtube_sync.module.synchronize.domain.{
  LocalPlayListVideoRepository,
  PlayListRepository,
  RemotePlayListVideoRepository
}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}

abstract class YoutubeSyncIntegrationTest extends IntegrationTest[YoutubeSyncModule] {

  override def context(): YoutubeSyncModule = new TestYoutubeSyncModule()

  override def cleanupBeforeTest(context: YoutubeSyncModule): TestResult = {
    val contentRootDirectory = File(context.configuration.contentRootPath)
    if (contentRootDirectory.exists) {
      contentRootDirectory.delete(swallowIOExceptions = false)
    }
  }

  def configuration(implicit module: YoutubeSyncModule): YoutubeSyncConfiguration =
    module.configuration

  def executionContext(implicit context: YoutubeSyncModule): ExecutionContext =
    context.ec
  def playListRepository(implicit context: YoutubeSyncModule): PlayListRepository =
    context.synchronizeModule.playListRepository
  def remotePlayListVideoRepository(implicit context: YoutubeSyncModule): RemotePlayListVideoRepository =
    context.synchronizeModule.remotePlayListVideoRepository
  def localPlayListVideoRepository(implicit context: YoutubeSyncModule): LocalPlayListVideoRepository =
    context.synchronizeModule.localPlayListVideoRepository
}

abstract class IntegrationTest[Context]
    extends WordSpec
    with ScalaFutures
    with Matchers
    with TypeCheckedTripleEquals
    with IntegrationPatience {
  type TestResult = Unit
  type Test       = Context => TestResult

  def context(): Context

  def runWithContext(testToExecute: Test): TestResult = {
    val newContext = context()
    cleanupBeforeTest(newContext)
    testToExecute(newContext)
    cleanupAfterTest(newContext)
  }

  def cleanupBeforeTest(context: Context): Unit = {}
  def cleanupAfterTest(context: Context): Unit  = {}
}
