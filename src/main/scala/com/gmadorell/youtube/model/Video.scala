package com.gmadorell.youtube.model

final case class Video(videoId: VideoId, name: VideoName)

final case class VideoId(id: String)

final case class VideoName(name: String)
