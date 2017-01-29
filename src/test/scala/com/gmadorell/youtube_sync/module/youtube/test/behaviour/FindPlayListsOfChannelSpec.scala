package com.gmadorell.youtube_sync.module.youtube.test.behaviour

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.shared.stub.SetStub
import com.gmadorell.youtube_sync.module.youtube.application.playlist.FindPlayListsOfChannelQueryHandler
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.youtube.test.stub.{
  ChannelIdStub,
  FindPlayListsOfChannelQueryStub,
  FindPlayListsOfChannelResponseStub,
  PlayListIdStub
}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures

final class FindPlayListsOfChannelSpec extends WordSpec with MockFactory with ScalaFutures with Matchers {

  private implicit val ec = scala.concurrent.ExecutionContext.global

  "A FindPlayListsOfChannelQueryHandler" should {
    "find the playlists of a channel" in {
      val repository = mock[PlayListRepository]

      val query           = FindPlayListsOfChannelQueryStub.random
      val playListsOfUser = SetStub.random(PlayListIdStub.random)

      val queryHandler = new FindPlayListsOfChannelQueryHandler(repository)

      (repository.findPlayLists _)
        .expects(ChannelIdStub.create(query.channelId))
        .once()
        .returning(Future.successful(playListsOfUser))

      val expectedResponse = FindPlayListsOfChannelResponseStub.create(query.channelId, playListsOfUser.map(_.id))
      queryHandler.handle(query).futureValue shouldBe expectedResponse
    }
  }
}
