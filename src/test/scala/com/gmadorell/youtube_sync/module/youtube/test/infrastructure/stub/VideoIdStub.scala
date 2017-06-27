package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.shared.stub.StringStub
import com.gmadorell.youtube_sync.module.youtube.domain.model.VideoId

object VideoIdStub {
  def create(videoId: String = StringStub.random(10)): VideoId = VideoId(videoId)

  def random(): VideoId = create()
}
