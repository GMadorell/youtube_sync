package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube.model.{PlayListId => ApiPlayListId}
import com.gmadorell.youtube_sync.module.youtube.domain.RemotePlayListVideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model._

final class ApiRemotePlayListVideoRepository(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext)
    extends RemotePlayListVideoRepository {

  override def search(playListId: PlayListId): Future[List[PlayListVideo]] =
    youtubeApi
      .playList(ApiPlayListId(playListId.id))
      .flatMap(
        _.fold(Future.successful(List.empty[PlayListVideo]))(
          playList =>
            youtubeApi
              .videos(ApiPlayListId(playListId.id))
              .map(apiVideos =>
                apiVideos.toList.map(apiVideo =>
                  PlayListVideo(PlayList(playListId, PlayListName(playList.name.name)),
                                Video(VideoId(apiVideo.videoId.id), VideoName(apiVideo.name.name)))))))
}
