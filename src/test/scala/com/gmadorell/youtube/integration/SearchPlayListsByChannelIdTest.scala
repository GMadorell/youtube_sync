package com.gmadorell.youtube.integration

import com.gmadorell.youtube.model.{ChannelId, PlayList, PlayListId, PlayListName}

final class SearchPlayListsByChannelIdTest extends YoutubeApiIntegrationTest {
  "A YoutubeApi" should {
    "be able to find a channel playlists by its id" in {
      val expectedPlayLists = config.test.playLists.map(testPlayList =>
        PlayList(PlayListId(testPlayList.playListId), PlayListName(testPlayList.name)))

      youtubeApi
        .playLists(ChannelId(config.test.dummyChannelId))
        .futureValue should contain theSameElementsAs expectedPlayLists
    }
  }
}
