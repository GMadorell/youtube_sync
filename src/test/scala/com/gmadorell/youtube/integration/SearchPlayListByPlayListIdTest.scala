package com.gmadorell.youtube.integration

import com.gmadorell.youtube.model.{PlayList, PlayListId, PlayListName}

final class SearchPlayListByPlayListIdTest extends YoutubeApiIntegrationTest {
  "A YoutubeApi" should {
    "be able to find a playlist by playlist id" in {
      val playList = config.test.playLists
        .map(testPlayList => PlayList(PlayListId(testPlayList.playListId), PlayListName(testPlayList.name)))
        .head

      youtubeApi.playList(playList.id).futureValue should ===(Some(playList))
    }
  }
}
