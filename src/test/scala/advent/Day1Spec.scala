package advent

import org.scalatest._
import advent.solutions._

final class Day1Spec extends FlatSpec with Matchers {

  it should "get 2 for a mass of 12" in {
    Day1.fuel(12) should be(2)
  }

  it should "get 2 for a mass of 14" in {
    Day1.fuel(14) should be(2)
  }

  it should "get 654 for a mass of 1969" in {
    Day1.fuel(1969) should be(654)
  }

  it should "get 33583 for a mass of 100756" in {
    Day1.fuel(100756) should be(33583)
  }
}
