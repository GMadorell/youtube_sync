package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube.model.{PlayListId => ApiPlayListId}
import com.gmadorell.youtube_sync.module.youtube.domain.VideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, Video, VideoId, VideoName}

final class ApiVideoRepository(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext) extends VideoRepository {
  override def findVideos(playListId: PlayListId): Future[Set[Video]] =
    youtubeApi.videos(ApiPlayListId(playListId.id)).map { videos =>
      videos.map(video => Video(VideoId(video.videoId.id), VideoName(video.name.name)))
    }
}
