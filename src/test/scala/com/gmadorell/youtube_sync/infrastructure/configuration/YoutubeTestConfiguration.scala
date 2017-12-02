package com.gmadorell.youtube_sync.infrastructure.configuration

import scala.collection.JavaConverters._

import com.typesafe.config.Config

final class YoutubeTestConfiguration(config: Config) {

  case class TestConfiguration(dummyChannelId: String, playLists: Seq[TestPlayListConfiguration])
  case class TestVideo(videoId: String, videoName: String)
  case class TestPlayListConfiguration(playListId: String, name: String, videos: Seq[TestVideo])

  val apiKey: String = config.getString("youtube-api.api-key")
  lazy val test: TestConfiguration = TestConfiguration(
    dummyChannelId = config.getString("test.dummy-user.channel-id"),
    playLists = config
      .getConfigList("test.dummy-user.playlists")
      .asScala
      .map(playListConfig =>
        TestPlayListConfiguration(
          name = playListConfig.getString("name"),
          playListId = playListConfig.getString("id"),
          videos = playListConfig
            .getConfigList("videos")
            .asScala
            .map(videoConfig =>
              TestVideo(videoId = videoConfig.getString("id"), videoName = videoConfig.getString("name")))
      ))
  )
}
