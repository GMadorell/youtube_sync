package com.gmadorell.api

import scala.concurrent.{ExecutionContext, Future}

import com.gmadorell.api.channel.ChannelId
import com.gmadorell.api.playlist.{PlayList, PlayListId, RosPlayListRepository}
import com.gmadorell.api.video.{RosVideoRepository, Video}
import monix.execution.Scheduler

final class YoutubeApi(private val apiKey: String)(implicit ec: ExecutionContext, scheduler: Scheduler) {
  private val playListsRepository = new RosPlayListRepository(apiKey)
  private val videoRepository     = new RosVideoRepository(apiKey)

  def playLists(channelId: ChannelId): Future[Set[PlayList]] = playListsRepository.playLists(channelId)
  def videos(playListId: PlayListId): Future[Set[Video]]     = videoRepository.videos(playListId)
}
