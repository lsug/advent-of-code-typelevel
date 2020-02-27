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

    final case class Entry(index: Int, value: Int)

    private val additionCode = 1
    private val multiplicationCode = 2
    private val lookupAndAdd = lookupAndOperate(_ + _)(_, _)
    private val lookupAndMultiply = lookupAndOperate(_ * _)(_, _)

    private def repair(initial: Int, program: List[Int]): List[Int] = {
      if (program(initial) == additionCode)
        repairProgram(program, lookupAndAdd(initial, program))
      else if (program(initial) == multiplicationCode)
        repairProgram(program, lookupAndMultiply(initial, program))
      else program
    }

    private def lookupAndOperate(
        f: (Int, Int) => Int
    )(initial: Int, program: List[Int]): Entry = {
      Entry(
        program(initial + 3),
        f(program(program(initial + 1)), program(program(initial + 2)))
      )
    }

    private def repairProgram(
        program: List[Int],
        entry: Entry
    ): List[Int] = {
      program.updated(entry.index, entry.value)
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

      val nouns = LazyList.range(0, 100, 1)
      val verbs = LazyList.range(0, 100, 1)
      val inputs: LazyList[Input] =
        nouns.flatMap(n => verbs.map(v => Input(n, v)))

      def check(input: Input): Boolean = {
        val modifiedProgram = generateUpdatedProgram(input, program)
        val result = run(modifiedProgram)(0)
        if (result == output) true else false
      }

      val out = inputs.find(check(_) == true) match {
        case Some(x) => x
        case None    => throw new Exception("No solution exists.")
      }
      out
    }

    def generateUpdatedProgram(input: Input, program: List[Int]): List[Int] = {
      val z1 = program.updated(1, input.noun)
      z1.updated(2, input.verb)
    }
  }

  def main(args: Array[String]): Unit = {

    // Copy the puzzle input from https://adventofcode.com/2019/day/2/input
    val puzzleInput: List[Int] = List(1, 12, 2, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5,
      0, 3, 2, 10, 1, 19, 1, 19, 9, 23, 1, 23, 6, 27, 1, 9, 27, 31, 1, 31, 10,
      35, 2, 13, 35, 39, 1, 39, 10, 43, 1, 43, 9, 47, 1, 47, 13, 51, 1, 51, 13,
      55, 2, 55, 6, 59, 1, 59, 5, 63, 2, 10, 63, 67, 1, 67, 9, 71, 1, 71, 13,
      75, 1, 6, 75, 79, 1, 10, 79, 83, 2, 9, 83, 87, 1, 87, 5, 91, 2, 91, 9, 95,
      1, 6, 95, 99, 1, 99, 5, 103, 2, 103, 10, 107, 1, 107, 6, 111, 2, 9, 111,
      115, 2, 9, 115, 119, 2, 13, 119, 123, 1, 123, 9, 127, 1, 5, 127, 131, 1,
      131, 2, 135, 1, 135, 6, 0, 99, 2, 0, 14, 0)

    // Solve your puzzle using the functions in parts 1 and 2
    val part1 = Day2.Part1.run(puzzleInput)(0)
    println(part1)
    val part2 = Day2.Part2.inputForOutput(puzzleInput, 19690720)
    println(100 * part2.noun + part2.verb)
  }
}
