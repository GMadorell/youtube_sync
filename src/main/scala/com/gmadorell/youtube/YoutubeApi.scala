package com.gmadorell.youtube

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.youtube.model.{ChannelId, PlayList, PlayListId, Video}
import com.gmadorell.youtube.playlist.PlayListSearcher
import com.gmadorell.youtube.video.VideoSearcher
import monix.execution.Scheduler

final class YoutubeApi(private val apiKey: String)(implicit ec: ExecutionContext) {
  implicit private val scheduler = Scheduler(ec)
  private val playListSearcher   = new PlayListSearcher(apiKey)
  private val videoSearcher      = new VideoSearcher(apiKey)

  def playLists(channelId: ChannelId): Future[Set[PlayList]] = playListSearcher.playLists(channelId)
  def videos(playListId: PlayListId): Future[Set[Video]]     = videoSearcher.videos(playListId)
}
