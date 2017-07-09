package com.gmadorell.youtube_sync.module.youtube.domain.model

final case class Video(id: VideoId, name: VideoName)

final case class VideoId(id: String) extends AnyVal

final case class VideoName(name: String) extends AnyVal
