package advent.solutions

import scala.annotation.tailrec

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
      val len = program.length - 1

      @tailrec
      def go(accum: List[Int], num: Int): List[Int] = {
        if (num > len) accum else go(repair(num, accum), num + 4)
      }

      go(program, 0)
    }

    private def repair(initial: Int, program: List[Int]): List[Int] = {
      if (program(initial) == 1) repairProgram(program, decodeOne(initial, program))
      else if (program(initial) == 2) repairProgram(program, decodeTwo(initial, program))
      else program
    }

    private def decodeOne(initial: Int, program: List[Int]): (Int, Int) = {
      (program(initial + 3), program(program(initial + 1)) + program(program(initial + 2)))
    }

    private def decodeTwo(initial: Int, program: List[Int]): (Int, Int) = {
      (program(initial + 3), program(program(initial + 1)) * program(program(initial + 2)))
    }

    private def repairProgram(program: List[Int], decoder: (Int, Int)): List[Int] = {
      program.updated(decoder._1, decoder._2)
    }
  }

  object Part2 {
    import Day2.Part1.run

    /** Represents the two numbers provided at addresses 1 and 2 of an Intcode program */
    final case class Input(noun: Int, verb: Int)

    /** Calculates the input required to produce a given output
     *
     * @param program A list of opcodes and positions.  The positions at address 1 and 2 will be replaced with input
     * @param output  The given output of the program
     * @return The input that would be entered in the program to produce the given output
     */
    def inputForOutput(program: List[Int], output: Int): Input = {
      val input1 = Input(0, 0)
      val modifiedProgram: List[Int] = generateUpdatedProgram(input1, program)
      if (check(run(modifiedProgram))) input1 else input1

    }

    def inputGenerator() = ???


    def generateUpdatedProgram(input: Input, program: List[Int]): List[Int] = {
      val z1 = program.updated(1, input.noun)
      z1.updated(2, input.verb)
    }

    def check(decodedProgram: List[Int]): Boolean = if (decodedProgram(0) == 19690720) true else false

  }

  def main(args: Array[String]): Unit = {

    // Copy the puzzle input from https://adventofcode.com/2019/day/2/input
    val puzzleInput: List[Int] = List(1,99,99,4,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,1,19,9,23,1,23,6,27,1,9,27,31,1,31,10,35,2,13,35,39,1,39,10,43,1,43,9,47,1,47,13,51,1,51,13,55,2,55,6,59,1,59,5,63,2,10,63,67,1,67,9,71,1,71,13,75,1,6,75,79,1,10,79,83,2,9,83,87,1,87,5,91,2,91,9,95,1,6,95,99,1,99,5,103,2,103,10,107,1,107,6,111,2,9,111,115,2,9,115,119,2,13,119,123,1,123,9,127,1,5,127,131,1,131,2,135,1,135,6,0,99,2,0,14,0)


    //(12, 1) => 4138657
    //(12, 2) => 4138658
    // Solve your puzzle using the functions in parts 1 and 2
    val part1 = Day2.Part1.run(puzzleInput)(0)
    println(part1)
  }
}
