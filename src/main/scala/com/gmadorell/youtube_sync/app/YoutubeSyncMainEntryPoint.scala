package com.gmadorell.youtube_sync.app

import com.gmadorell.youtube_sync.infrastructure.dependency_injection.ProductionYoutubeSyncModule

object YoutubeSyncMainEntryPoint extends App {
  val module = new ProductionYoutubeSyncModule()
  module.setupActors()
}
