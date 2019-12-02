package de.ungefroren.adventofcode.y2019.day01

import de.ungefroren.adventofcode.y2019.day01.Day01B.getClass

import scala.io.Source

object Day01A {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  def fuelOfModule(mass: Int) : Int = (mass / 3) - 2

  def main(args: Array[String]): Unit = {
    var total = 0L;
    input.getLines().map(_.toInt).map(fuelOfModule).foreach(total += _)
    println(s"Part 1: Required fuel: $total")
  }
}
