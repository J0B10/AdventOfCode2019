package de.ungefroren.adventofcode.y2019.day02

import de.ungefroren.adventofcode.y2019.intcodeComputer.IntcodeComputer
import de.ungefroren.adventofcode.y2019.intcodeComputer.instructions.defaults

import scala.io.Source

object Day02A {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))


  def main(args: Array[String]): Unit = {
    val computer = IntcodeComputer(input)
    computer.memory(1) = 12
    computer.memory(2) = 2
    computer.run()
    val result = computer.memory(0)
    print(s"Value at position 0 is: $result")
  }

}
