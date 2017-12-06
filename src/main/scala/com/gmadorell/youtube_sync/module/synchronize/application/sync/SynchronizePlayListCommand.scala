package com.gmadorell.youtube_sync.module.synchronize.application.sync

import com.gmadorell.bus.model.command.Command

final case class SynchronizePlayListCommand(playListId: String) extends Command
