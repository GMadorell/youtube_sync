package com.gmadorell.youtube_sync.module.youtube.test.behaviour

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{ChannelId, PlayListId}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, OneInstancePerTest, WordSpec}
import org.scalatest.concurrent.ScalaFutures

trait YoutubeBehaviourSpec extends WordSpec with MockFactory with ScalaFutures with Matchers with OneInstancePerTest {
  val playListRepository: PlayListRepository = mock[PlayListRepository]

  def shouldFindPlayListsOfChannel(channelId: ChannelId, playListsOfChannel: Set[PlayListId]): Unit = {
    (playListRepository.findPlayLists _)
      .expects(channelId)
      .once()
      .returning(Future.successful(playListsOfChannel))
  }
}
