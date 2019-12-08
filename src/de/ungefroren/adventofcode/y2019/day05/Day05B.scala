package de.ungefroren.adventofcode.y2019.day05

import de.ungefroren.adventofcode.y2019.intcodeComputer.IntcodeComputer
import de.ungefroren.adventofcode.y2019.intcodeComputer.instructions.defaults

import scala.io.Source

object Day05B {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  def main(args: Array[String]): Unit = {
    IntcodeComputer(input).run()
  }
}
