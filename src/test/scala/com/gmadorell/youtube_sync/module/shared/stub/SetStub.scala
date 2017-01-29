package com.gmadorell.youtube_sync.module.shared.stub

object SetStub {
  def random[SetValueT](builder: => SetValueT, amountOfItems: Int = NumberStub.random): Set[SetValueT] = {
    (1 to amountOfItems).map(_ => builder).toSet
  }
}
