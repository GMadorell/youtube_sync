package com.gmadorell.youtube_sync.module.youtube.test.infrastructure.stub

import com.gmadorell.youtube_sync.module.shared.stub.StringStub
import com.gmadorell.youtube_sync.module.youtube.domain.model.{Video, VideoId, VideoName}

object VideoStub {
  def create(id: VideoId = VideoIdStub.random(), name: VideoName = VideoNameStub.random()): Video =
    Video(id, name)

  def random(): Video = create()
}

object VideoIdStub {
  def create(videoId: String = StringStub.random(10)): VideoId = VideoId(videoId)

  def random(): VideoId = create()
}

object VideoNameStub {
  def create(name: String = StringStub.random(10)): VideoName =
    VideoName(name)

  def random(): VideoName = create()
}
