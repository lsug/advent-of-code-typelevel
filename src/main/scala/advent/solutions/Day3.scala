package advent.solutions

import advent.solutions.Day3.WireParseError.{InvalidDirection, InvalidDistance}

import scala.annotation.tailrec

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
    val texts: List[(String, String)] =
      text
        .split(",")
        .map(t => (t.filter(_.isLetter), t.filter(!_.isLetter)))
        .toList

    checkTexts(texts) match {
      case "allValid" =>
        Right(
          Wire(texts.map(t => Displacement(whichDirection(t._1), t._2.toInt)))
        )
      case "invalidDirection" => Left(InvalidDirection(text))
      case "invalidDistance"  => Left(InvalidDistance(text))
    }
  }

  private def checkTexts(texts: List[(String, String)]): String = {
    if (texts.forall(t => isValidDirection(t._1))) {
      if (texts.forall(t => t._2.toInt > 0)) "allValid"
      else "invalidDistance"
    } else "invalidDirection"
  }

  private def isValidDirection(s: String): Boolean =
    s == "L" | s == "R" | s == "U" | s == "D"

  private def whichDirection(s: String): Direction = {
    import Direction._
    s match {
      case "L" => Left
      case "R" => Right
      case "U" => Up
      case "D" => Down
    }
  }

  final case class Coordinate(x: Int, y: Int)

  final case class StartingAndEndingPoints(start: Coordinate, end: Coordinate)

  final case class TurningPoint(point: Coordinate)

  final case class Path(points: Seq[Coordinate])

  final case class WireMap(
      displacement: Displacement,
      startingAndEndingPoints: StartingAndEndingPoints
  )

  final case class PathLocations(displacement: Displacement, path: Path)

  import Direction._

  /**
    *
    * @param wire
    * @return
    */
  def findTurningPoints(wire: Wire): List[TurningPoint] = {

    @tailrec
    def go(
        accum: List[Coordinate],
        currentCoordinate: Coordinate,
        displacements: List[Displacement]
    ): List[TurningPoint] = displacements match {
      case x :: xs =>
        go(
          nextTurningPoint(x, currentCoordinate) +: accum,
          nextTurningPoint(x, currentCoordinate),
          xs
        )
      case Nil => accum.reverse.map(TurningPoint)
    }
    go(List(Coordinate(0, 0)), Coordinate(0, 0), wire.path)
  }

  /**
    *
    * @param displacement
    * @param currentCoordinate
    * @return
    */
  def nextTurningPoint(
      displacement: Displacement,
      currentCoordinate: Coordinate
  ): Coordinate = {
    import Direction._
    displacement.direction match {
      case Right =>
        Coordinate(
          currentCoordinate.x + displacement.distance,
          currentCoordinate.y
        )
      case Up =>
        Coordinate(
          currentCoordinate.x,
          currentCoordinate.y + displacement.distance
        )
      case Left =>
        Coordinate(
          currentCoordinate.x - displacement.distance,
          currentCoordinate.y
        )
      case Down =>
        Coordinate(
          currentCoordinate.x,
          currentCoordinate.y - displacement.distance
        )
    }
  }

  /**
    *
    * @param wire
    * @return
    */
  def pairEachDisplacementWithStartingAndEndingCoordinates(
      wire: Wire
  ): List[WireMap] = {
    val turningPoints: List[TurningPoint] = findTurningPoints(wire)
    val len = turningPoints.length - 2

    @tailrec
    def go(
        accum: List[WireMap],
        num: Int
    ): List[WireMap] = {
      if (num > len) accum.reverse
      else
        go(
          WireMap(
            wire.path(num),
            StartingAndEndingPoints(
              turningPoints(num).point,
              turningPoints(num + 1).point
            )
          ) +: accum,
          num + 1
        )
    }

    go(List[WireMap](), 0)
  }

  /**
    *
    * @param displacement
    * @param path
    * @return
    */
  def pathTravelled(
      displacement: Displacement,
      path: StartingAndEndingPoints
  ): Path = {
    val out = displacement.direction match {
      case Right =>
        (path.start.x to path.end.x).map(d => Coordinate(d, path.start.y))
      case Left =>
        (path.end.x to path.start.x).map(d => Coordinate(d, path.start.y))
      case Up =>
        (path.start.y to path.end.y).map(d => Coordinate(path.start.x, d))
      case Down =>
        (path.end.y to path.start.y).map(d => Coordinate(path.start.x, d))
    }
    Path(out)
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
      val wire0Coordinates: List[Coordinate] =
        pairEachDisplacementWithStartingAndEndingCoordinates(
          wire0
        ).flatMap(i =>
          pathTravelled(i.displacement, i.startingAndEndingPoints).points
        )

      val wire1Coordinates =
        pairEachDisplacementWithStartingAndEndingCoordinates(
          wire1
        ).flatMap(i =>
          pathTravelled(i.displacement, i.startingAndEndingPoints).points
        )

      val intersectionPoints = wire0Coordinates
        .intersect(wire1Coordinates)
        .filterNot(_ == Coordinate(0, 0))

      if (intersectionPoints.isEmpty) None
      else Some(intersectionPoints.map(c => math.abs(c.x) + math.abs(c.y)).min)
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
      val out = intersectionPoints(wire0, wire1) match {
        case Some(x) => {
          val result = x.map(intersectionPoint => {
            countNumberOfStepsToAnIntersection(wire0, intersectionPoint) + countNumberOfStepsToAnIntersection(
              wire1,
              intersectionPoint
            )
          })
          Some(result.min)
        }
        case None => None
      }
      out
    }

    /**
      *
      * @param wire
      * @param intersectionPoint
      * @return
      */
    def countNumberOfStepsToAnIntersection(
        wire: Wire,
        intersectionPoint: Coordinate
    ): Int = {
      val initialLocationDetails: List[WireMap] =
        pairEachDisplacementWithStartingAndEndingCoordinates(wire)

      val wirePathTraveled: List[PathLocations] =
        initialLocationDetails.map(i =>
          PathLocations(
            i.displacement,
            Path(
              pathTravelled(i.displacement, i.startingAndEndingPoints).points
            )
          )
        )

      val selectedDisplacement = wirePathTraveled
        .filter(m => m.path.points.contains(intersectionPoint))
        .head
        .displacement

      val allDisplacements = initialLocationDetails.map(_.displacement)
      val index = allDisplacements.indexWhere(_ == selectedDisplacement)
      val previousSteps: Int =
        wirePathTraveled
          .take(index)
          .map(m => m.displacement.distance)
          .sum
      val currentStep: WireMap = initialLocationDetails(index)
      val diffX = math.abs(
        currentStep.startingAndEndingPoints.start.x - intersectionPoint.x
      )
      val diffY = math.abs(
        currentStep.startingAndEndingPoints.start.y - intersectionPoint.y
      )
      val step = diffX + diffY
      previousSteps + step
    }

    /**
      *
      * @param wire0
      * @param wire1
      * @return
      */
    def intersectionPoints(
        wire0: Wire,
        wire1: Wire
    ): Option[List[Coordinate]] = {

      val wireCoordinates = List(wire0, wire1).map(w =>
        pairEachDisplacementWithStartingAndEndingCoordinates(w)
          .flatMap(i =>
            pathTravelled(i.displacement, i.startingAndEndingPoints).points
          )
      )

      val intersectionPoints = wireCoordinates.head
        .intersect(wireCoordinates.last)
        .filterNot(_ == Coordinate(0, 0))

      if (intersectionPoints.isEmpty) None
      else Some(intersectionPoints)
    }

  }

  import Part1._
  import Part2._

  def main(args: Array[String]): Unit = {

    // Copy the puzzle input from https://adventofcode.com/2019/day/3/input
    val puzzleWire0Input: String =
      "R992,U284,L447,D597,R888,D327,R949,U520,R27,U555,L144,D284,R538,U249,R323,U297,R136,U838,L704,D621,R488,U856,R301,U539,L701,U363,R611,D94,L734,D560,L414,U890,R236,D699,L384,D452,R702,D637,L164,U410,R649,U901,L910,D595,R339,D346,R959,U777,R218,D667,R534,D762,R484,D914,L25,U959,R984,D922,R612,U999,L169,D599,L604,D357,L217,D327,L730,D949,L565,D332,L114,D512,R460,D495,L187,D697,R313,U319,L8,D915,L518,D513,R738,U9,R137,U542,L188,U440,R576,D307,R734,U58,R285,D401,R166,U156,L859,U132,L10,U753,L933,U915,R459,D50,R231,D166,L253,U844,R585,D871,L799,U53,R785,U336,R622,D108,R555,D918,L217,D668,L220,U738,L997,D998,R964,D456,L54,U930,R985,D244,L613,D116,L994,D20,R949,D245,L704,D564,L210,D13,R998,U951,L482,U579,L793,U680,L285,U770,L975,D54,R79,U613,L907,U467,L256,D783,R883,U810,R409,D508,L898,D286,L40,U741,L759,D549,R210,U411,R638,D643,L784,U538,L739,U771,L773,U491,L303,D425,L891,U182,R412,U951,L381,U501,R482,D625,R870,D320,L464,U555,R566,D781,L540,D754,L211,U73,L321,D869,R994,D177,R496,U383,R911,U819,L651,D774,L591,U666,L883,U767,R232,U822,L499,U44,L45,U873,L98,D487,L47,U803,R855,U256,R567,D88,R138,D678,L37,U38,R783,U569,L646,D261,L597,U275,L527,U48,R433,D324,L631,D160,L145,D128,R894,U223,R664,U510,R756,D700,R297,D361,R837,U996,L769,U813,L477,U420,L172,U482,R891,D379,L329,U55,R284,U155,L816,U659,L671,U996,R997,U252,R514,D718,L661,D625,R910,D960,L39,U610,R853,U859,R174,U215,L603,U745,L587,D736,R365,U78,R306,U158,L813,U885,R558,U631,L110,D232,L519,D366,R909,D10,R294"
    val puzzleWire1Input: String =
      "L1001,D833,L855,D123,R36,U295,L319,D700,L164,U576,L68,D757,R192,D738,L640,D660,R940,D778,R888,U772,R771,U900,L188,D464,L572,U184,R889,D991,L961,U751,R560,D490,L887,D748,R37,U910,L424,D401,L385,U415,L929,U193,R710,D855,L596,D323,L966,D505,L422,D139,L108,D135,R737,U176,R538,D173,R21,D951,R949,D61,L343,U704,R127,U468,L240,D834,L858,D127,R328,D863,R329,U477,R131,U864,R997,D38,R418,U611,R28,U705,R148,D414,R786,U264,L785,D650,R201,D250,R528,D910,R670,U309,L658,U190,R704,U21,R288,D7,R930,U62,R782,U621,R328,D725,R305,U700,R494,D137,R969,U142,L867,U577,R300,U162,L13,D698,R333,U865,R941,U796,L60,U902,L784,U832,R78,D578,R196,D390,R728,D922,R858,D994,L457,U547,R238,D345,R329,D498,R873,D212,R501,U474,L657,U910,L335,U133,R213,U417,R698,U829,L2,U704,L273,D83,R231,D247,R675,D23,L692,D472,L325,D659,L408,U746,L715,U395,L596,U296,R52,D849,L713,U815,R684,D551,L319,U768,R176,D182,R557,U731,R314,D543,L9,D256,R38,D809,L567,D332,R375,D572,R81,D479,L71,U968,L831,D247,R989,U390,R463,D576,R740,D539,R488,U367,L596,U375,L763,D824,R70,U448,R979,D977,L744,D379,R488,D671,L516,D334,L542,U517,L488,D390,L713,D932,L28,U924,L448,D229,L488,D501,R19,D910,L979,D411,R711,D824,L973,U291,R794,D485,R208,U370,R655,U450,L40,D804,L374,D671,R962,D829,L209,U111,L84,D876,L832,D747,L733,D560,L702,D972,R188,U817,L111,U26,L492,U485,L71,D59,L269,D870,L152,U539,R65,D918,L932,D260,L485,U77,L699,U254,R924,U643,L264,U96,R395,D917,R360,U354,R101,D682,R854,U450,L376,D378,R872,D311,L881,U630,R77,D766,R672"

    // Solve your puzzle using the functions in parts 1 and 2
    println(
      distanceToIntersection(
        parse(puzzleWire0Input).getOrElse(Wire(List())),
        parse(puzzleWire1Input).getOrElse(Wire(List()))
      )
    )

    println(
      numberOfStepsToIntersection(
        parse(puzzleWire0Input).getOrElse(Wire(List())),
        parse(puzzleWire1Input).getOrElse(Wire(List()))
      )
    )

  }
}
