package de.ungefroren.adventofcode.y2019.day02

import de.ungefroren.adventofcode.y2019.intcodeComputer.IntcodeComputer
import de.ungefroren.adventofcode.y2019.intcodeComputer.instructions.defaults

import scala.io.Source

object Day02A {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))


  def main(args: Array[String]): Unit = {
    val intcode = input.mkString.replaceAll("\\s", "").split(",").map(_.toInt)
    intcode(1) = 12
    intcode(2) = 2
    val computer = IntcodeComputer(intcode)
    computer.run()
    val result = computer.memory(0)
    print(s"Value at position 0 is: $result")
  }

}
