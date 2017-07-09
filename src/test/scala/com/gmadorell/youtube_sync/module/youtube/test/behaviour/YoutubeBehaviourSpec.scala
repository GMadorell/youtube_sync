package com.gmadorell.youtube_sync.module.youtube.test.behaviour

import scala.concurrent.Future

import com.gmadorell.bus.domain.event.EventBus
import com.gmadorell.bus.model.event.Event
import com.gmadorell.youtube_sync.module.youtube.domain.{PlayListRepository, PlayListVideoRepository, VideoRepository}
import com.gmadorell.youtube_sync.module.youtube.domain.model._
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, OneInstancePerTest, WordSpec}
import org.scalatest.concurrent.ScalaFutures

trait YoutubeBehaviourSpec extends WordSpec with MockFactory with ScalaFutures with Matchers with OneInstancePerTest {
  val playListRepository: PlayListRepository           = mock[PlayListRepository]
  val videoRepository: VideoRepository                 = mock[VideoRepository]
  val playListVideoRepository: PlayListVideoRepository = mock[PlayListVideoRepository]
  val eventBus: EventBus                               = mock[EventBus]

  def shouldFindPlayListsOfChannel(channelId: ChannelId, playListsOfChannel: Set[PlayList]): Unit = {
    (playListRepository.findPlayLists _)
      .expects(channelId)
      .once()
      .returning(Future.successful(playListsOfChannel))
  }

  def shouldFindVideosOfPlayList(playListId: PlayListId, videos: Set[Video]): Unit = {
    (videoRepository.findVideos _)
      .expects(playListId)
      .once()
      .returning(Future.successful(videos))
  }

  def shouldPublishEvent(event: Event): Unit = {
    (eventBus.handle _)
      .expects(event)
      .once()
      .returning(Right(Future.successful(())))
  }

  def playListVideoShouldExist(playList: PlayList, video: Video): Unit =
    (playListVideoRepository.exists _)
      .expects(playList, video)
      .once()
      .returning(Future.successful(true))

  def playListVideoShouldNotExist(playList: PlayList, video: Video): Unit =
    (playListVideoRepository.exists _)
      .expects(playList, video)
      .once()
      .returning(Future.successful(false))

  def shouldCreatePlayListVideo(playList: PlayList, video: Video): Unit =
    (playListVideoRepository.create _)
      .expects(playList, video)
      .once()
      .returning(Future.successful(()))
}
