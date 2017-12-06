package com.gmadorell.youtube.integration

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeTestConfiguration
import com.typesafe.config.ConfigFactory
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}

trait YoutubeApiIntegrationTest
    extends WordSpec
    with ScalaFutures
    with IntegrationPatience
    with Matchers
    with TypeCheckedTripleEquals {
  implicit val ec = scala.concurrent.ExecutionContext.global

  val config     = new YoutubeTestConfiguration(ConfigFactory.load("test.conf"))
  val youtubeApi = new YoutubeApi(config.apiKey)
}
