package com.gmadorell.youtube_sync.module.youtube.test.behaviour.sync

import com.gmadorell.youtube_sync.module.shared.stub.ListStub
import com.gmadorell.youtube_sync.module.youtube.application.sync.{
  PlayListSynchronizer,
  SynchronizePlayListCommandHandler
}
import com.gmadorell.youtube_sync.module.youtube.domain.error.PlayListNotFound
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, PlayListVideo}
import com.gmadorell.youtube_sync.module.youtube.test.behaviour.YoutubeBehaviourSpec
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub.{
  PlayListIdStub,
  PlayListStub,
  PlayListVideoStub,
  SynchronizePlayListCommandStub
}

final class SynchronizePlayListSpec extends YoutubeBehaviourSpec {

  val handler = new SynchronizePlayListCommandHandler(
    new PlayListSynchronizer(playListRepository, remotePlayListVideoRepository, localPlayListVideoRepository))

  "A SynchronizePlayListCommandHandler" should {
    "create videos that exist in the remote but not locally" in {
      val command = SynchronizePlayListCommandStub.create()

      val playListId                 = PlayListIdStub.create(command.playListId)
      val playList                   = PlayListStub.create(playListId)
      val videosBothInRemoteAndLocal = randomPlayListVideos(playList)
      val onlyRemoteVideos           = randomPlayListVideos(playList)
      val onlyLocalVideos            = randomPlayListVideos(playList)
      val remoteVideos               = onlyRemoteVideos ++ videosBothInRemoteAndLocal
      val localVideos                = onlyLocalVideos ++ videosBothInRemoteAndLocal

      shouldFindPlayList(playListId, playList)
      shouldFindRemotePlayListVideos(playList, remoteVideos)
      shouldFindLocalPlayListVideos(playList, localVideos)
      onlyRemoteVideos.foreach(shouldCreateLocalPlayListVideo)

      handler.handle(command).futureValue.isRight should ===(true)
    }

    "fail when the playlist doesn't exist" in {
      val command = SynchronizePlayListCommandStub.create()

      val playListId = PlayListIdStub.create(command.playListId)

      shouldNotFindPlayList(playListId)

      val result = handler.handle(command).futureValue
      result.isLeft should ===(true)
      result.left.map(error => error should ===(PlayListNotFound(playListId)))
    }
  }

  def randomPlayListVideos(playList: PlayList): List[PlayListVideo] =
    ListStub.randomElements(() => PlayListVideoStub.create(playList))
}
