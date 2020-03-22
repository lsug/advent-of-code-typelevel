package advent.solutions

/** Day 2: 1202 Program Alarm
  *
  * @see https://adventofcode.com/2019/day/2
  */
object Day2 {

  object Part1 {

    type Result[A] = Either[Error, A]

    type Op = (Int, Int) => Int

    /** Runs an Intcode program
      *
      * @param program A list of opcodes and positions
      * @return The program after having been run on itself
      */
    def run(
        program: List[Int]
    ): List[Int] = {
      val start: Int = 0
      val step: Int = 4
      val indexList = List.range(start, program.length, step)
      indexList.foldLeft(program)((p, opcodeIndex) => runOp(p, opcodeIndex))
    }

    private val additionCode: Int = 1
    private val multiplicationCode: Int = 2
    private val terminationCode: Int = 99

    /** Get and run a single operation on the program
      *
      * @param program      The program
      * @param opcodeIndex  The index of the operation to run.
      *                     The opcode is located at `opcodeIndex`.
      *                     The index of the first value to operate on is located at `opcodeIndex + 1`.
      *                     The index of the second value to operate on is located at `opcodeIndex + 2`.
      *                     The index to store the result at is located at `opcodeIndex + 3`.
      */
    private def runOp(
        program: List[Int],
        opcodeIndex: Int
    ): List[Int] = {
      lookupOp(program, opcodeIndex)
        .map(operate(opcodeIndex, program, _))
        .getOrElse(program)
    }

    /** Get the operation to run on the program
      *
      * @param program      The program
      * @param opcodeIndex  The index where the opcode is located.
      * @return             A multiplication or addition function corresponding to the opcode
                            Or an error in the case of termination or an unrecognized opcode
      */
    private def lookupOp(
        program: List[Int],
        opcodeIndex: Int
    ): Option[Op] = {
      val opcode = lookup(program, opcodeIndex)
      opcode match {
        case `additionCode`       => Some(_ + _)
        case `multiplicationCode` => Some(_ * _)
        case `terminationCode`    => None
      }
    }

    /** Run an operation on a program
      *
      * @param program      The program
      * @param opcodeIndex  The index where the opcode is located.
      * @param op           The function corresponding to the operation
      * @return             A modified program
      */
    private def operate(
        opcodeIndex: Int,
        program: List[Int],
        op: Op
    ): List[Int] = {
      val xIndex = lookup(program, opcodeIndex + 1)
      val yIndex = lookup(program, opcodeIndex + 2)
      val storeIndex = lookup(program, opcodeIndex + 3)
      val x = lookup(program, xIndex)
      val y = lookup(program, yIndex)
      store(program, storeIndex, op(x, y))
    }

    /** Gets the value at an index */
    private def lookup(program: List[Int], i: Int): Int = {
      program(i)
    }

    /** Stores a value at an index */
    private def store(
        program: List[Int],
        i: Int,
        v: Int
    ): List[Int] = {
      program.updated(i, v)
    }
  }

  object Part2 {

    /** Represents the two numbers provided at addresses 1 and 2 of an Intcode program */
    final case class Input(noun: Int, verb: Int)

    /** Calculates the input required to produce a given output
      *
      * @param program A list of opcodes and positions.  The positions at address 1 and 2 will be replaced with input
      * @param output  The given output of the program
      * @return The input that would be entered in the program to produce the given output
      */
    def inputForOutput(program: List[Int], output: Int): Option[Input] = {
      ???
    }

    // Uncomment this to solve part 2
    // /** Stores the noun and verb at addresses 1 and 2 of the program */
    // private def setInput[P](input: Input, program: P)(
    //     implicit I: Index[P, Int, Int]
    // ): P = {
    //   ???
    // }
  }

  // scalastyle:off
  @SuppressWarnings(Array("org.wartremover.warts.All"))
  def main(args: Array[String]): Unit = {

    // Copy the puzzle input from https://adventofcode.com/2019/day/2/input
    // Solve your puzzle using the functions in parts 1 and 2
  }
  // scalastyle:on
}
