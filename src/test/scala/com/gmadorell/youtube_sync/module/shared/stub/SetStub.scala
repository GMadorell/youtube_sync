package com.gmadorell.youtube_sync.module.shared.stub

object SetStub {
  def random[T](builder: () => T, amountOfItems: Int = NumberStub.randomIntUpToInclusive(15)): Set[T] = {
    (1 to amountOfItems).map(_ => builder()).toSet
  }
}
