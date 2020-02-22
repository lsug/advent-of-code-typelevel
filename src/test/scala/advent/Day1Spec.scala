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
}
