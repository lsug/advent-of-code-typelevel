package advent

import org.scalatest._
import advent.solutions._

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

final class Day1Spec extends AnyFlatSpec with Matchers {

  "Part 1" should "have a mass of 2 for a module of mass 12" in {
    Day1.Part1.fuel(12) should be(2)
  }

  it should "have a mass of 2 for a module of mass 14" in {
    Day1.Part1.fuel(14) should be(2)
  }

  it should "have a mass of 654 for a module of mass 1969" in {
    Day1.Part1.fuel(1969) should be(654)
  }

  it should "have a mass of 33583 for a module of mass 100756" in {
    Day1.Part1.fuel(100756) should be(33583)
  }

  "Part 2" should "have a mass of 2 for a module of mass 14" in {
    Day1.Part2.totalFuel(14) should be(2)
  }

  it should "have a mass of 966 for a module of mass 1969" in {
    Day1.Part2.totalFuel(1969) should be(966)
  }

  it should "have a mass of 50346 for a module of mass 100756" in {
    Day1.Part2.totalFuel(100756) should be(50346)
  }
}
