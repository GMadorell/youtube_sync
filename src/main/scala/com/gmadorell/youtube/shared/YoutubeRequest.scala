package com.gmadorell.youtube.shared

import fr.hmil.roshttp.{HttpRequest, Method, Protocol}

object YoutubeRequest {
  val host = "www.googleapis.com/youtube/v3/"

  val baseRequest: HttpRequest = HttpRequest()
    .withProtocol(Protocol.HTTPS)
    .withHost(host)
    .withMethod(Method.GET)
}
