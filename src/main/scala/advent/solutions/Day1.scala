package advent.solutions

import scala.annotation.tailrec
import cats._
import cats.implicits._

/** Day 1: The Tyranny of the Rocket Equation
  *
  * @see https://adventofcode.com/2019/day/1
  */
object Day1 {

  trait Mass[A] {
    def quotient(a: A, b: A): A
    def minus(a: A, b: A): A
    def two: A
    def three: A
  }

  object implicits {
    implicit val intHasMass: Mass[Int] = new Mass[Int] {
      def quotient(a: Int, b: Int): Int = a / b
      def minus(a: Int, b: Int): Int = a - b
      def two: Int = 2
      def three: Int = 3
    }
  }

  object Part1 {

    /** Calculates the fuel required to launch a module of a given mass
      *
      * @param mass The mass of the module
      * @return The fuel required to to launch the module
      */
    def fuel[A](mass: A)(implicit M: Mass[A]): A = {
      M.minus(M.quotient(mass, M.three), M.two)
    }

    /** Calculates the sum of the fuel required to launch each module of a given mass
      *
      * @param masses The masses of each module
      * @return The sum of the fuel required to launch each module
      */
    def sumOfFuel[F[_], A](
        masses: F[A]
    )(implicit M: Mass[A], N: Monoid[A], G: Functor[F], H: Foldable[F]): A = {
      sumFuels(calculateFuels(masses))
    }

    private def calculateFuels[F[_], A](
        masses: F[A]
    )(implicit M: Mass[A], G: Functor[F]): F[A] = {
      masses.map(fuel[A])
    }

    private def sumFuels[F[_], A](
        fuels: F[A]
    )(implicit M: Monoid[A], F: Foldable[F]): A = {
      fuels.fold
    }
  }

  object Part2 {

    /** Calculates the total required to launch a module, including the fuel required to launch the fuel itself
      *
      * @param mass The mass of the module
      * @return The fuel required to launch the module, plus the fuel required to launch that fuel
      */
    def totalFuel[A](mass: A)(implicit M: Monoid[A], O: Order[A]): A = {

      @tailrec
      def go(currentFuel: A, accum: A): A =
        if (currentFuel < M.empty) accum
        else go(mass, accum combine currentFuel)

      go(mass, M.empty)

    }

    /** Calculates the sum of the total fuel required to launch each module of a given mass
      *
      * @param masses The masses of each module
      * @return The sum of the total fuel required to launch each module
      */
    def sumOfTotalFuel[F[_], A](
        masses: F[A]
    )(implicit M: Monoid[A], O: Order[A], G: Functor[F], H: Foldable[F]): A = {
      masses.map(totalFuel[A]).fold
    }
  }

  // scalastyle:off
  @SuppressWarnings(Array("org.wartremover.warts.All"))
  def main(args: Array[String]): Unit = {

    import Day1.implicits._

    // Copy the puzzle input from https://adventofcode.com/2019/day/1/input
    val puzzleInput: List[Int] = Nil

    // Solve your puzzle using the functions in parts 1 and 2
    val part1 = Part1.sumOfFuel(puzzleInput)
    println(part1)
    val part2 = Part2.sumOfTotalFuel(puzzleInput)
    println(part2)
  }
  // scalastyle:on
}
