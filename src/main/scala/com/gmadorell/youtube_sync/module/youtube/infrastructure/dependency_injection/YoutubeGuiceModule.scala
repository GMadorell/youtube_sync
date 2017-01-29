package com.gmadorell.youtube_sync.module.youtube.infrastructure.dependency_injection

import com.gmadorell.youtube_sync.module.youtube.domain.PlayListRepository
import com.gmadorell.youtube_sync.module.youtube.infrastructure.ApiPlayListRepository
import net.codingwell.scalaguice.ScalaModule
import com.google.inject.Singleton

final class YoutubeGuiceModule extends ScalaModule {
  override def configure(): Unit = {
    bind[PlayListRepository].to[ApiPlayListRepository].in[Singleton]
  }
}
