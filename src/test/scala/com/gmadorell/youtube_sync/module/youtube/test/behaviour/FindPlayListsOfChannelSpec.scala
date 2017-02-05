package com.gmadorell.youtube_sync.module.youtube.test.behaviour

import com.gmadorell.youtube_sync.module.shared.stub.SetStub
import com.gmadorell.youtube_sync.module.youtube.application.playlist.FindPlayListsOfChannelQueryHandler
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub.{
  ChannelIdStub,
  FindPlayListsOfChannelQueryStub,
  FindPlayListsOfChannelResponseStub,
  PlayListIdStub
}

final class FindPlayListsOfChannelSpec extends YoutubeBehaviourSpec {

  private implicit val ec = scala.concurrent.ExecutionContext.global

  "A FindPlayListsOfChannelQueryHandler" should {
    "find the playlists of a channel" in {
      val query              = FindPlayListsOfChannelQueryStub.random
      val playListsOfChannel = SetStub.random(PlayListIdStub.random)
      val expectedResponse   = FindPlayListsOfChannelResponseStub.create(query.channelId, playListsOfChannel.map(_.id))

      val queryHandler = new FindPlayListsOfChannelQueryHandler(playListRepository)

      shouldFindPlayListsOfChannel(ChannelIdStub.create(query.channelId), playListsOfChannel)

      queryHandler.handle(query).futureValue shouldBe expectedResponse
    }
  }
}
