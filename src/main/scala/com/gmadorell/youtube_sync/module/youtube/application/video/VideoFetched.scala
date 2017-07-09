package com.gmadorell.youtube_sync.module.youtube.application.video

import com.gmadorell.bus.model.event.Event

final case class VideoFetched(channelId: String,
                              playListId: String,
                              playListName: String,
                              videoId: String,
                              videoName: String)
    extends Event
