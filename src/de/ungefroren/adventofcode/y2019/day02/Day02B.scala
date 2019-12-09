package de.ungefroren.adventofcode.y2019.day02

import de.ungefroren.adventofcode.y2019.intcodeComputer.IntcodeComputer
import de.ungefroren.adventofcode.y2019.intcodeComputer.instructions.defaults

import scala.io.Source

object Day02B {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  val targetOutput = 19690720

  def main(args: Array[String]): Unit = {
    val intcode = input.mkString.replaceAll("\\s", "").split(",").map(_.toInt)
    for (noun <- 0 to 99) {
      for (verb <- 0 to 99) {
        val program = intcode.clone
        program(1) = noun
        program(2) = verb
        val computer = IntcodeComputer(program)
        computer.run()
        val result = computer.memory(0)
        if (result == targetOutput) {
          print(s"Traget output of $targetOutput was generated using noun=$noun and verb=$verb. Result: ${100 * noun + verb}")
          return
        }
      }
    }
  }
}
