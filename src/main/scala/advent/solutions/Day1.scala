package advent.solutions

import scala.annotation.tailrec

/** Day 1: The Tyranny of the Rocket Equation
 *
 * @see https://adventofcode.com/2019/day/1
 */
object Day1 {
  // TODO: Write the code to solve day 1 here

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
    // This should use the Day1.fuel function above
    masses.map(fuel).sum
  }

  /** Calculates the total required to launch a module, including the fuel required to launch the fuel itself
   *
   * @param mass The mass of the module
   * @return The fuel required to launch the module, plus the fuel required to launch that fuel
   */
  def totalFuel(mass: Int): Int = {
    @tailrec
    def go(currentFuel: Int, accum: Int): Int = if (currentFuel < 0) accum else go(fuel(currentFuel), accum + currentFuel)

    go(fuel(mass), 0)

  }

  /** Calculates the sum of the total fuel required to launch each module of a given mass
   *
   * @param masses The masses of each module
   * @return The sum of the total fuel required to launch each module
   */
  def sumOfTotalFuel(masses: List[Int]): Int = {
    // This should use the Day1.totalFuel function above
    masses.map(totalFuel).sum
  }

}
