package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.Future

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListVideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayListId, VideoId}

final class ApiPlayListVideoRepository(youtubeApi: YoutubeApi) extends PlayListVideoRepository {

  /*
   TODO The name should already be given as parameter here! Rework the api so that we get the name
   on the first request we do, and then pass video objects around instead of simply the id
   */

  override def exists(playListId: PlayListId, videoId: VideoId): Future[Boolean] =
    Future.successful(false) // TODO

  override def create(playListId: PlayListId, videoId: VideoId): Future[Unit] =
    Future.successful(()) // TODO
}
