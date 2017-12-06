package com.gmadorell.youtube_sync.module.synchronize.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.synchronize.domain.model.{PlayList, PlayListVideo, Video}

object PlayListVideoStub {
  def create(playList: PlayList = PlayListStub.create(), video: Video = VideoStub.create()): PlayListVideo =
    PlayListVideo(playList, video)
}
