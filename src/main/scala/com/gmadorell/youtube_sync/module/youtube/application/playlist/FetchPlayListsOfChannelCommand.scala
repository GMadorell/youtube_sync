package com.gmadorell.youtube_sync.module.youtube.application.playlist

import com.gmadorell.bus.model.command.Command

final case class FetchPlayListsOfChannelCommand(channelId: String) extends Command
