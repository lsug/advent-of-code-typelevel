package advent.solutions

/** Day 2: 1202 Program Alarm
  *
  * @see https://adventofcode.com/2019/day/2
  */
object Day2 {

  object Part1 {

    /** Runs an Intcode program
      *
      * @param program A list of opcodes and positions
      * @return The program after having been run on itself
      */
    def run(program: List[Int]): List[Int] = {
      ???
    }
  }

  object Part2 {

    /** Represents the two numbers provided at addresses 1 and 2 of an Intcode program */
    final case class Input(noun: Int, verb: Int)

    /** Calculates the input required to produce a given output
      *
      * @param program A list of opcodes and positions.  The positions at address 1 and 2 will be replaced with input
      * @param output  The given output of the program
      *
      * @return The input that would be entered in the program to produce the given output
      */
    def inputForOutput(program: List[Int], output: Int): Input = ???
  }

  def main(args: Array[String]): Unit = {

    // Copy the puzzle input from https://adventofcode.com/2019/day/2/input
    val puzzleInput: List[Int] = ???

    // Solve your puzzle using the functions in parts 1 and 2
    ???
  }
}
