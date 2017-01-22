package com.gmadorell.youtube

import scala.concurrent.Future

import com.gmadorell.youtube.channel.ChannelId
import com.gmadorell.youtube.playlist.{PlayList, PlayListId, RosPlayListRepository}
import com.gmadorell.youtube.video.{RosVideoRepository, Video}
import monix.execution.Scheduler

final class YoutubeApi(private val apiKey: String)(implicit scheduler: Scheduler) {
  private val playListsRepository = new RosPlayListRepository(apiKey)
  private val videoRepository     = new RosVideoRepository(apiKey)

  def playLists(channelId: ChannelId): Future[Set[PlayList]] = playListsRepository.playLists(channelId)
  def videos(playListId: PlayListId): Future[Set[Video]]     = videoRepository.videos(playListId)
}
