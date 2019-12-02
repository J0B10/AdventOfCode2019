package de.ungefroren.adventofcode.y2019.day01

import scala.io.Source
import scala.annotation.tailrec

object Day01B {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  def fuelOfModule(mass: Int) : Int = (mass / 3) - 2

  @tailrec
  def includingFuelMass(fuel: Int, mass: Int) : Int = {
    val fuel_forMass = fuelOfModule(mass)
    if (fuel_forMass > 0) {
      includingFuelMass(fuel + fuel_forMass, fuel_forMass)
    } else {
      fuel
    }
  }

  def main(args: Array[String]): Unit = {
    var total = 0L;
    input.getLines.map(_.toInt).map(includingFuelMass(0, _)).foreach(total += _)
    println(s"Part 2: Required fuel: $total")
  }
}
