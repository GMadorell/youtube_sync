package com.gmadorell.youtube_sync.module.shared.stub

object SetStub {
  def random[SetValueT](builder: => SetValueT,
                        amountOfItems: Int = NumberStub.randomIntUpToInclusive(15)): Set[SetValueT] = {
    (1 to amountOfItems).map(_ => builder).toSet
  }
}
