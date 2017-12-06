package com.gmadorell.youtube_sync.module.synchronize.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube.model.{PlayListId => ApiPlayListId}
import com.gmadorell.youtube_sync.module.synchronize.domain.RemotePlayListVideoRepository
import com.gmadorell.youtube_sync.module.synchronize.domain.model._

final class ApiRemotePlayListVideoRepository(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext)
    extends RemotePlayListVideoRepository {

  override def search(playList: PlayList): Future[List[PlayListVideo]] =
    youtubeApi
      .videos(ApiPlayListId(playList.id.id))
      .map(apiVideos =>
        apiVideos.toList.map(apiVideo =>
          PlayListVideo(playList, Video(VideoId(apiVideo.videoId.id), VideoName(apiVideo.name.name)))))
}
