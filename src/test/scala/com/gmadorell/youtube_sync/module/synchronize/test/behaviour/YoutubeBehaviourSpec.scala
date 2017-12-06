package com.gmadorell.youtube_sync.module.synchronize.test.behaviour

import scala.concurrent.Future

import com.gmadorell.youtube_sync.module.synchronize.domain._
import com.gmadorell.youtube_sync.module.synchronize.domain.model._
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, OneInstancePerTest, WordSpec}
import org.scalatest.concurrent.ScalaFutures

trait YoutubeBehaviourSpec extends WordSpec with MockFactory with ScalaFutures with Matchers with OneInstancePerTest {

  implicit val ec = scala.concurrent.ExecutionContext.global

  val playListRepository: PlayListRepository                       = mock[PlayListRepository]
  val remotePlayListVideoRepository: RemotePlayListVideoRepository = mock[RemotePlayListVideoRepository]
  val localPlayListVideoRepository: LocalPlayListVideoRepository   = mock[LocalPlayListVideoRepository]

  def shouldFindPlayList(playListId: PlayListId, playList: PlayList): Unit =
    (playListRepository.search _)
      .expects(playListId)
      .once()
      .returning(Future.successful(Some(playList)))

  def shouldNotFindPlayList(playListId: PlayListId): Unit =
    (playListRepository.search _)
      .expects(playListId)
      .once()
      .returning(Future.successful(None))

  def shouldFindRemotePlayListVideos(playList: PlayList, playListVideos: List[PlayListVideo]): Unit =
    (remotePlayListVideoRepository.search _)
      .expects(playList)
      .once()
      .returning(Future.successful(playListVideos))

  def shouldFindLocalPlayListVideos(playList: PlayList, playListVideos: List[PlayListVideo]): Unit =
    (localPlayListVideoRepository.search _)
      .expects(playList)
      .once()
      .returning(Future.successful(playListVideos))

  def shouldCreateLocalPlayListVideo(playListVideo: PlayListVideo): Unit =
    (localPlayListVideoRepository.create _)
      .expects(playListVideo)
      .once()
      .returning(Future.successful(()))
}
