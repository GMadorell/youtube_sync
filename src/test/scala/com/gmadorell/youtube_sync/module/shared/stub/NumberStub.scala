package com.gmadorell.youtube_sync.module.shared.stub

import scala.util.Random

object NumberStub {
  def randomInt: Int = Random.nextInt()

  def randomBoundedIntInclusive(lowerBound: Int, upperBound: Int): Int =
    lowerBound + Random.nextInt((upperBound - lowerBound) + 1)

  def randomIntUpToInclusive(upperBound: Int): Int =
    randomBoundedIntInclusive(0, upperBound)

}
