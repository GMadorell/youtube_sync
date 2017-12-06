package com.gmadorell.youtube_sync.module.synchronize.infrastructure

import scala.concurrent.{ExecutionContext, Future}
import scala.sys.process._

import better.files._
import com.gmadorell.youtube_sync.infrastructure.configuration.YoutubeSyncConfiguration
import com.gmadorell.youtube_sync.module.synchronize.domain.LocalPlayListVideoRepository
import com.gmadorell.youtube_sync.module.synchronize.domain.model._

final class FilesystemLocalPlayListVideoRepository(config: YoutubeSyncConfiguration)(implicit ec: ExecutionContext)
    extends LocalPlayListVideoRepository {

  /*
   * Assumption:
   *   Videos will be stored in path:
   *     - root / playListName / videoName__videoId.mp3
   */

  override def search(playList: PlayList): Future[List[PlayListVideo]] = {
    val directory = playListDirectory(playList.name)
    val foundPlayListVideos = if (directory.exists) {
      directory.children
        .filter(_.isRegularFile)
        .map(file => PlayListVideo(playList, deconstructFileName(file.name)))
        .toList
    } else {
      List.empty
    }
    Future.successful(foundPlayListVideos)
  }

  override def create(playListVideo: PlayListVideo): Future[Unit] = Future {
    val youtubeUrl                = s"https://www.youtube.com/watch?v=${playListVideo.video.id.id}"
    val youtubeDLFileNameTemplate = constructFileName(playListVideo.video).replace(".mp3", ".%(ext)s")
    val downloadPath              = playListDirectory(playListVideo.playList.name) / youtubeDLFileNameTemplate
    val youtubeDLCommand =
      Seq("youtube-dl",
          "--extract-audio",
          "--audio-format=mp3",
          s"--output=${downloadPath.path.toString}",
          s"$youtubeUrl")
    val process    = Process(youtubeDLCommand)
    val statusCode = process.!(ProcessLogger.apply(string => println(string)))
    if (statusCode != 0) {
      throw YoutubeDLException(
        s"Youtube DL failed to download playListVideo: ($playListVideo), statusCode: $statusCode")
    }
  }

  private def playListDirectory(playListName: PlayListName): File =
    config.contentRootPath / playListName.name

  private def deconstructFileName(fileName: String): Video = {
    val fileNameRegex = """(.*)__(.*)\.mp3""".r
    fileName match {
      case fileNameRegex(videoName, videoId) => Video(VideoId(videoId), VideoName(videoName))
    }
  }

  private def constructFileName(video: Video): String =
    s"${video.name.name}__${video.id.id}.mp3"
}

final case class YoutubeDLException(message: String) extends RuntimeException(message)
