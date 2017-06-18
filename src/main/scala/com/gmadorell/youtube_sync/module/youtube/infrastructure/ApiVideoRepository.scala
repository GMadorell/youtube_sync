package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube.model.{PlayListId => ApiPlayListId}
import com.gmadorell.youtube_sync.module.youtube.domain.VideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, VideoId}

final class ApiVideoRepository(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext) extends VideoRepository {
  override def findVideos(playListId: PlayListId): Future[Set[VideoId]] =
    youtubeApi.videos(ApiPlayListId(playListId.id)).map { videos =>
      videos.map(video => VideoId(video.videoId.id))
    }
}
