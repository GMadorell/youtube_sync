package com.gmadorell.youtube_sync.infrastructure.configuration

import com.typesafe.config.Config
import scala.collection.JavaConverters._

final class Configuration(private val config: Config) {

  // TODO - single test configuration! we have test playlists split
  case class TestPlayList(playListId: String, videos: Seq[String])
  case class TestPlayListConfiguration(playListId: String, name: String)
  case class TestConfiguration(dummyChannelId: String,
                               playListsOfDummyChannel: Seq[TestPlayListConfiguration],
                               testPlayList: TestPlayList)

  val apiKey: String          = config.getString("youtube-api.api-key")
  val channels: Seq[String]   = config.getStringList("context.channels").asScala
  val playLists: Seq[String]  = config.getStringList("context.playlists").asScala
  val contentRootPath: String = config.getString("file-system.content-root-path")
  val test: TestConfiguration = TestConfiguration(
    dummyChannelId = config.getString("test.dummy-user.channel-id"),
    playListsOfDummyChannel =
      config
        .getConfigList("test.dummy-user.playlists")
        .asScala
        .map(playListConfig =>
          TestPlayListConfiguration(name = playListConfig.getString("name"),
                                    playListId = playListConfig.getString("id"))),
    testPlayList = TestPlayList(
      playListId = config.getString("test.dummy-user.test-playlist.playlist-id"),
      videos = config.getStringList("test.dummy-user.test-playlist.video-ids").asScala
    )
  )
}
