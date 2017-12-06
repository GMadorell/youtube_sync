package com.gmadorell.youtube_sync.module.shared.stub

import scala.util.Random

object ListStub {
  def randomElements[A](creator: () => A): List[A] =
    randomElements(Random.nextInt(100), creator)

  def atLeastOne[A](creator: () => A): List[A] =
    atLeast(1)(creator)

  def atLeast[A](n: Int)(creator: () => A): List[A] =
    randomElements(Random.nextInt(100) + n, creator)

  def randomElementsCappedAt[A](maximumElements: Int, creator: () => A): List[A] =
    randomElements(Random.nextInt(maximumElements), creator)

  def randomElements[A](amount: Int, creator: () => A): List[A] =
    (0 until amount).toList.map(_ => creator())
}
