package com.gmadorell.youtube_sync.module.youtube.infrastructure

import scala.concurrent.Future

import better.files.File
import com.gmadorell.youtube_sync.module.youtube.domain.PlayListVideoRepository
import com.gmadorell.youtube_sync.module.youtube.domain.model.{PlayList, Video}

import better.files._


final class FilesystemPlayListVideoRepository(basePath: String) extends PlayListVideoRepository {

  private val basePathFile = File(basePath)

  override def exists(playList: PlayList, video: Video): Future[Boolean] =
    Future.successful(false) // TODO

  override def create(playList: PlayList, video: Video): Future[Unit] =
    Future.successful(()) // TODO

  private def obtainPath(playList: PlayList, video: Video): File = {
//    basePathFile / playList.name
    ???
  }

}
