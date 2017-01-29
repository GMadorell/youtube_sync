package com.gmadorell.youtube_sync.module.shared.stub

import scala.util.Random

object StringStub {
  def random(numChars: Int): String = {
    Random.alphanumeric take numChars mkString ""
  }

  def randomNumeric(numChars: Int): String = {
    Random.nextLong.toString take numChars
  }

  def random(numCharsRange: Range): String = {
    val numChars = Random.nextInt(numCharsRange.end)

    if (numChars < numCharsRange.start) random(numCharsRange)
    else random(numChars)
  }

  def randomNotNumeric(numChars: Int): String = {
    def isAllDigits(x: String): Boolean = x forall Character.isDigit

    val randomStringCandidate = random(numChars)

    if (isAllDigits(randomStringCandidate)) randomNotNumeric(numChars)
    else randomStringCandidate
  }
}
