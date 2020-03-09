package advent.solutions

import scala.annotation.tailrec

/** Day 1: The Tyranny of the Rocket Equation
  *
  * @see https://adventofcode.com/2019/day/1
  */
object Day1 {

  object Part1 {

    /** Calculates the fuel required to launch a module of a given mass
      *
      * @param mass The mass of the module
      * @return The fuel required to to launch the module
      */
    def fuel(mass: Int): Int = {
      (mass / 3) - 2
    }

    /** Calculates the sum of the fuel required to launch each module of a given mass
      *
      * @param masses The masses of each module
      * @return The sum of the fuel required to launch each module
      */
    def sumOfFuel(masses: List[Int]): Int = {
      masses.map(fuel).sum
    }

  }

  object Part2 {

    /** Calculates the total required to launch a module, including the fuel required to launch the fuel itself
      *
      * @param mass The mass of the module
      * @return The fuel required to launch the module, plus the fuel required to launch that fuel
      */
    def totalFuel(mass: Int): Int = {
      @tailrec
      def go(currentFuel: Int, accum: Int): Int =
        if (currentFuel < 0) accum
        else go(Part1.fuel(currentFuel), accum + currentFuel)

      go(Part1.fuel(mass), 0)

    }

    /** Calculates the sum of the total fuel required to launch each module of a given mass
      *
      * @param masses The masses of each module
      * @return The sum of the total fuel required to launch each module
      */
    def sumOfTotalFuel(masses: List[Int]): Int = {
      masses.map(totalFuel).sum
    }
  }

  // scalastyle:off
  @SuppressWarnings(Array("org.wartremover.warts.All"))
  def main(args: Array[String]): Unit = {

    // Copy the puzzle input from https://adventofcode.com/2019/day/1/input
    val puzzleInput: List[Int] = List(1, 12, 2, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5,
      0, 3, 2, 10, 1, 19, 1, 19, 9, 23, 1, 23, 6, 27, 1, 9, 27, 31, 1, 31, 10,
      35, 2, 13, 35, 39, 1, 39, 10, 43, 1, 43, 9, 47, 1, 47, 13, 51, 1, 51, 13,
      55, 2, 55, 6, 59, 1, 59, 5, 63, 2, 10, 63, 67, 1, 67, 9, 71, 1, 71, 13,
      75, 1, 6, 75, 79, 1, 10, 79, 83, 2, 9, 83, 87, 1, 87, 5, 91, 2, 91, 9, 95,
      1, 6, 95, 99, 1, 99, 5, 103, 2, 103, 10, 107, 1, 107, 6, 111, 2, 9, 111,
      115, 2, 9, 115, 119, 2, 13, 119, 123, 1, 123, 9, 127, 1, 5, 127, 131, 1,
      131, 2, 135, 1, 135, 6, 0, 99, 2, 0, 14, 0)

    // Solve your puzzle using the functions in parts 1 and 2
    val part1 = Part1.sumOfFuel(puzzleInput)
    println(part1)
    val part2 = Part2.sumOfTotalFuel(puzzleInput)
    println(part2)
  }
  // scalastyle:on
}
