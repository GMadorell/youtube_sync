package com.gmadorell.youtube_sync.module.synchronize.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.youtube.YoutubeApi
import com.gmadorell.youtube.model.{PlayListId => ApiPlayListId}
import com.gmadorell.youtube_sync.module.synchronize.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.synchronize.domain.model.{PlayList, PlayListId, PlayListName}

final class ApiPlayListRepository(youtubeApi: YoutubeApi)(implicit ec: ExecutionContext) extends PlayListRepository {

  override def search(playListId: PlayListId): Future[Option[PlayList]] =
    youtubeApi
      .playList(ApiPlayListId(playListId.id))
      .map(maybeApiPlayList =>
        maybeApiPlayList.map(apiPlayList =>
          PlayList(PlayListId(apiPlayList.id.id), PlayListName(apiPlayList.name.name))))
}
