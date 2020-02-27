package advent.solutions

/** Day 3: Crossed Wires
  *
  * @see https://adventofcode.com/2019/day/3
  */
object Day3 {

  /** The direction that a wire travels in */
  sealed trait Direction

  object Direction {
    case object Left extends Direction
    case object Right extends Direction
    case object Up extends Direction
    case object Down extends Direction
  }

  /** The displacement that a wire travels
    *
    * This is comprised of a direction and a positive distance
    */
  final case class Displacement(direction: Direction, distance: Int)

  final case class Wire(path: List[Displacement])

  /** Any error which occurs when creating a [[Wire]] from a string of text */
  sealed trait WireParseError

  object WireParseError {

    /** The first character of a displacement does not represent a direction e.g. "F45"
      *
      * @param direction  The invalid direction e.g "F" in "F45"
      */
    final case class InvalidDirection(direction: String) extends WireParseError

    /** The distance characters do not represent a positive integer e.g "R-32" or "RL"
      *
      * @param distance The invalid distance e.g "-32" in "R-32"
      */
    final case class InvalidDistance(distance: String) extends WireParseError
  }

  /** Parse a string input into a Wire
    *
    * Parsing is the process of taking a string and analysing it to produce a datatype
    *
    * @param text A comma separated string of displacements e.g "R75,D30,R83"
    */
  def parse(text: String): Either[WireParseError, Wire] = {
    ???
  }

  object Part1 {

    /** The manhattan distance to the closest intersection
      *
      * The closest intersection has the smallest manhattan distance
      *
      * @param  wire0  The first wire
      * @param  wire1  The second wire
      * @return The distance to the closest intersection if the wires intersect
      *         or [[None]] if they don't intersect at all
      */
    def distanceToIntersection(wire0: Wire, wire1: Wire): Option[Int] = {
      ???
    }

  }

  object Part2 {

    /** The steps to the closest intersection
      *
      * The closest intersection has the smallest number of steps
      *
      * @param  wire0  The first wire
      * @param  wire1  The second wire
      * @return The number of steps to the closest intersection if the wires intersect
      *         or [[None]] if they don't intersect at all
      */
    def numberOfStepsToIntersection(wire0: Wire, wire1: Wire): Option[Int] = {
      ???
    }
  }

  def main(args: Array[String]): Unit = {

    // Copy the puzzle input from https://adventofcode.com/2019/day/3/input
    val puzzleWire0Input: String = ???
    val puzzleWire1Input: String = ???

    // Solve your puzzle using the functions in parts 1 and 2
    ???
  }
}
