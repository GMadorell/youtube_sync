package com.gmadorell.youtube_sync.util.matcher

import scala.concurrent.{Await, Future}
import scala.util.{Either, Try}

import org.scalatest.Matchers
import org.scalatest.matchers.{BeMatcher, MatchResult, Matcher}
import scala.concurrent.duration._

trait EitherMatchers extends Matchers {

  def right: BeMatcher[Either[_, _]]                 = new RightBeMatcher()
  def left: BeMatcher[Either[_, _]]                  = not(right)
  def beRight[T](expected: T): Matcher[Either[_, T]] = new RightElementMatcher[T](expected)
  def beLeft[T](expected: T): Matcher[Either[T, _]]  = new LeftElementMatcher[T](expected)

  def successfulFuture: BeMatcher[Either[_, Future[_]]] = new SuccessfulFutureBeMatcher()
  def beSuccessfulFuture[T](expected: T): Matcher[Either[_, Future[T]]] =
    new SuccessfulFutureElementMatcher[T](expected)
}

object EitherMatchers extends EitherMatchers

private final class RightBeMatcher extends BeMatcher[Either[_, _]] {
  def apply(either: Either[_, _]): MatchResult = {
    MatchResult(
      either.isRight,
      s"$either wasn't a right",
      s"$either was a right"
    )
  }
}

private final class LeftElementMatcher[T](expected: T) extends Matcher[Either[T, _]] {
  def apply(either: Either[T, _]): MatchResult = {
    MatchResult(
      either.fold(_ == expected, _ => false),
      s"$either was not a left, or the left value did not match the expected element $expected",
      s"$either was a left, and did match the expected element $expected"
    )
  }
}

private final class RightElementMatcher[T](expected: T) extends Matcher[Either[_, T]] {
  def apply(either: Either[_, T]): MatchResult = {
    MatchResult(
      either.fold(_ == false, actual => actual == expected),
      s"$either was not a right, or the right value did not match the expected element $expected",
      s"$either was a right, and did match the expected element $expected"
    )
  }
}

private final class SuccessfulFutureBeMatcher() extends BeMatcher[Either[_, Future[_]]] {
  def apply(either: Either[_, Future[_]]): MatchResult =
    MatchResult(
      either.fold(_ => false, future => Try(Await.result(future, 1.second)).isSuccess),
      s"$either Either was a left, or a right with a failed future",
      s"$either Either was a right, with a successful future"
    )
}

private final class SuccessfulFutureElementMatcher[T](expected: T) extends Matcher[Either[_, Future[T]]] {
  def apply(either: Either[_, Future[T]]): MatchResult =
    MatchResult(
      either.fold(_ => false,
                  future => Try(Await.result(future, 1.second)).isSuccess && future.value.get.get == expected),
      s"$either Either was a left, a right with a failed future, or a right " +
        s"with a successful future but not matching expected $expected",
      s"$either Either was a right, with a successful future matching the expected value $expected"
    )
}
