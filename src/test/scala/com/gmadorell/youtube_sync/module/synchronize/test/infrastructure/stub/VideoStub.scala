package com.gmadorell.youtube_sync.module.synchronize.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.shared.stub.StringStub
import com.gmadorell.youtube_sync.module.synchronize.domain.model.{Video, VideoId, VideoName}

object VideoStub {
  def create(id: VideoId = VideoIdStub.create(), name: VideoName = VideoNameStub.create()): Video =
    Video(id, name)
}

object VideoIdStub {
  def create(videoId: String = StringStub.random(10)): VideoId = VideoId(videoId)
}

object VideoNameStub {
  def create(name: String = StringStub.random(10)): VideoName =
    VideoName(name)
}
