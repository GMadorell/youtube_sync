package com.gmadorell.youtube_sync.module.youtube.test.behaviour

import com.gmadorell.youtube_sync.module.shared.stub.ListStub
import com.gmadorell.youtube_sync.module.youtube.application.sync_new.{PlayListSynchronizer, SynchronizePlayListCommandHandler}
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, PlayListVideo}
import com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub.{PlayListIdStub, PlayListStub, PlayListVideoStub, SynchronizePlayListCommandStub}

final class SynchronizePlayListSpec extends YoutubeBehaviourSpec {

  val handler = new SynchronizePlayListCommandHandler(
    new PlayListSynchronizer(remotePlayListVideoRepository, localPlayListVideoRepository))

  "A SynchronizePlayListCommandHandler" should {
    "create videos that exist in the remote but not locally" in {
      val command = SynchronizePlayListCommandStub.create()

      val playListId                 = PlayListIdStub.create(command.playListId)
      val videosBothInRemoteAndLocal = randomPlayListVideos(playListId)
      val onlyRemoteVideos           = randomPlayListVideos(playListId)
      val onlyLocalVideos            = randomPlayListVideos(playListId)
      val remoteVideos               = onlyRemoteVideos ++ videosBothInRemoteAndLocal
      val localVideos                = onlyLocalVideos ++ videosBothInRemoteAndLocal

      shouldFindRemotePlayListVideos(playListId, remoteVideos)
      shouldFindLocalPlayListVideos(playListId, localVideos)
      onlyRemoteVideos.foreach(shouldCreateLocalPlayListVideo)

      handler.handle(command).futureValue.isRight should ===(true)
    }
  }

  def randomPlayListVideos(playListId: PlayListId): List[PlayListVideo] =
    ListStub.randomElements(() => PlayListVideoStub.create(PlayListStub.create(playListId)))
}
