package advent

import advent.solutions.Day3.Direction._
import advent.solutions.Day3._
import advent.solutions.Day3.Part2._
import org.scalatest._
import advent.solutions._

final class Day3Spec
    extends FlatSpec
    with Matchers
    with EitherValues
    with OptionValues {

  "part 1" should "find the distance to an intersection" in {
    val wire0: Day3.Wire = parse("R75,D30,R83,U83,L12,D49,R71,U7,L72")
    val wire1: Day3.Wire = parse("U62,R66,U55,R34,D71,R55,D58,R83")
    Day3.Part1.distanceToIntersection(wire0, wire1) shouldBe Some(159)
  }

  it should "find the distance to an intersection for another pair of wires" in {
    val wire0: Day3.Wire = parse("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51")
    val wire1: Day3.Wire = parse("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
    Day3.Part1.distanceToIntersection(wire0, wire1) shouldBe Some(135)
  }

  "part 2" should "find the number of steps to an intersection" in {
    val wire0: Day3.Wire = parse("R75,D30,R83,U83,L12,D49,R71,U7,L72")
    val wire1: Day3.Wire = parse("U62,R66,U55,R34,D71,R55,D58,R83")
    Day3.Part2.numberOfStepsToIntersection(wire0, wire1) shouldBe Some(610)
  }

  it should "find the number of steps to an intersection for another pair of wires" in {
    val wire0: Day3.Wire = parse("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51")
    val wire1: Day3.Wire = parse("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
    Day3.Part2.numberOfStepsToIntersection(wire0, wire1) shouldBe Some(410)
  }

  private def parse(text: String): Day3.Wire = {
    val either = Day3.parse(text)
    either shouldBe 'right
    either.right.value
  }

  private val _wire0 = Wire(
    List(
      Displacement(Right, 8),
      Displacement(Up, 5),
      Displacement(Left, 5),
      Displacement(Down, 3)
    )
  )
  private val _wire1 = Wire(
    List(
      Displacement(Up, 7),
      Displacement(Right, 6),
      Displacement(Down, 4),
      Displacement(Left, 4)
    )
  )

  "intersectionPoints" should "return 2 intersection points" in {
    assertResult(Some(List(Coordinate(6, 5), Coordinate(3, 3)))) {
      intersectionPoints(_wire0, _wire1)
    }
  }

}
