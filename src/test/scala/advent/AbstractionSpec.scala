package advent

import org.scalatest._
import advent.solutions._

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

final class AbstractionSpec extends AnyFlatSpec with Matchers {

  import Abstraction._

  "identity" should "return the same output as input" in {
    identity(true) should be(true)
    identity(1) should be(1)
    identity("apple") should be("apple")
  }

  "pair" should "apply to any pair of values" in {
    pair(1, true) should be((1, true))
    pair("apple", 1) should be(("apple", 1))
    pair(true, false) should be((true, false))
  }

  "first" should "apply to any pair of values" in {
    first(("apple", 1)) should be("apple")
    first((true, false)) should be(true)
  }

  "head" should "apply to any list" in {
    head(List(1, 2, 3)) should be(Some(1))
    head(List(true, false)) should be(Some(true))
    head(Nil) should be(None)
  }

  "getOrElse" should "apply to any option" in {
    getOrElse(Some(true), true) should be(true)
    getOrElse(Some("apple"), "pear") should be("apple")
    getOrElse(None, 2) should be(2)
  }

  "fold" should "apply to any option" in {
    fold(Some(true), x => x, false) should be(true)
    fold(Option.empty[Int], _ => true, false) should be(true)
    fold(Some("apple"), _.startsWith("a"), false) should be(true)
  }
}
