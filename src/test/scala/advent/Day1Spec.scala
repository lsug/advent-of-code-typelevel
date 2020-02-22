package advent

import org.scalatest._
import advent.solutions._

final class Day1Spec extends FlatSpec with Matchers {

  val input: List[Int] = List(
    141255, 93769, 122572, 149756, 72057, 82031, 60702, 124099, 135752, 115450,
    132370, 80491, 95738, 143495, 136550, 104549, 94588, 106213, 60223, 78219,
    60082, 118735, 127069, 101492, 110708, 141521, 117201, 61006, 117919, 90301,
    114235, 114314, 95737, 119737, 86881, 86544, 114809, 142720, 138854, 144712,
    133167, 87144, 106932, 111031, 112390, 109664, 66068, 50997, 141775, 73637,
    121700, 64790, 127751, 100007, 62234, 75611, 57855, 135729, 83746, 139042,
    112117, 76456, 129343, 50490, 146912, 77074, 147598, 149476, 101984, 85490,
    128768, 70178, 98111, 118362, 129962, 66553, 76347, 140614, 127431, 110969,
    138104, 118212, 107207, 79938, 144751, 142226, 116332, 65844, 124779,
    104634, 83143, 94999, 72677, 76926, 83956, 59617, 145999, 62619, 87955,
    143030
  )

  val puzzle1Input: List[Int] = List(80891,
    109412,
    149508,
    114894,
    97527,
    59858,
    113548,
    110516,
    97454,
    84612,
    84578,
    87923,
    102675,
    114312,
    144158,
    147190,
    53051,
    115477,
    50870,
    122198,
    91019,
    114350,
    88592,
    119617,
    61012,
    67012,
    85425,
    62185,
    124628,
    98505,
    53320,
    123834,
    105862,
    113715,
    149328,
    72125,
    107301,
    110684,
    86037,
    102012,
    133227,
    66950,
    64761,
    141015,
    132134,
    87171,
    84142,
    80355,
    124967,
    87973,
    98062,
    79312,
    120108,
    97537,
    89584,
    55206,
    68135,
    83286,
    66726,
    101805,
    72996,
    113109,
    116248,
    132007,
    128378,
    52506,
    113628,
    62277,
    73720,
    101756,
    141675,
    107011,
    81118,
    60598,
    122703,
    129905,
    67786,
    50982,
    96193,
    70006,
    137087,
    136121,
    146902,
    74781,
    50569,
    102645,
    99426,
    97857,
    122801,
    55022,
    81433,
    60509,
    66906,
    142099,
    126652,
    103240,
    141014,
    55579,
    143169,
    125521)

  "fuel" should "have a mass of 2 for a module of mass 12" in {
    Day1.fuel(12) should be(2)
  }

  it should "have a mass of 2 for a module of mass 14" in {
    Day1.fuel(14) should be(2)
  }

  it should "have a mass of 654 for a module of mass 1969" in {
    Day1.fuel(1969) should be(654)
  }

  it should "have a mass of 33583 for a module of mass 100756" in {
    Day1.fuel(100756) should be(33583)
  }

  it should "solve part 1 of the puzzle" in {
    Day1.sumOfFuel(input) should be(3497399)
  }

  it should "return answer for part 1 of the puzzle" in {
    Day1.sumOfFuel(puzzle1Input) should be (3267638)
  }

  "total fuel" should "have a mass of 2 for a module of mass 14" in {
    Day1.totalFuel(14) should be(2)
  }

  it should "have a mass of 966 for a module of mass 1969" in {
    Day1.totalFuel(1969) should be(654)
  }

  it should "have a mass of 50346 for a module of mass 100756" in {
    Day1.totalFuel(100756) should be(50346)
  }

  it should "solve part 2 of the puzzle" in {
    Day1.sumOfTotalFuel(input) should be(5243207)
  }

  it should "return answer for part 2 of the puzzle" in {
    Day1.sumOfTotalFuel(puzzle1Input) should be(4898585)
  }
}
