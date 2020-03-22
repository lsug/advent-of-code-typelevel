package advent

import org.scalatest._
import advent.solutions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

final class Day2Spec extends AnyFlatSpec with Matchers {

  "Part 1" should "run 1,0,0,0,99 to produce 2,0,0,0,99 (1 + 1 = 2)" in {
    Day2.Part1.run(List(1, 0, 0, 0, 99)) should be(Right(List(2, 0, 0, 0, 99)))
  }

  it should "run 2,3,0,3,99 to produce 2,3,0,6,99 (3 * 2 = 6)" in {
    Day2.Part1.run(List(2, 3, 0, 3, 99)) should be(Right(List(2, 3, 0, 6, 99)))
  }

  it should "run 2,4,4,5,99,0 to produce 2,4,4,5,99,9801 (99 * 99 = 9801)" in {
    Day2.Part1.run(List(2, 4, 4, 5, 99, 0)) should be(
      Right(List(2, 4, 4, 5, 99, 9801))
    )
  }

  it should "run 1,1,1,4,99,5,6,0,99 to produce 30,1,1,4,2,5,6,0,99" in {
    Day2.Part1.run(List(1, 1, 1, 4, 99, 5, 6, 0, 99)) should be(
      Right(List(30, 1, 1, 4, 2, 5, 6, 0, 99))
    )
  }
}
