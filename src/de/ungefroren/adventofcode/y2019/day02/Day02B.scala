package de.ungefroren.adventofcode.y2019.day02

import scala.annotation.tailrec
import scala.io.Source

object Day02B {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  val targetOutput = 19690720

  @tailrec
  def runIntcode(pos: Int, intcode: Array[Int]): Array[Int] = {
    val opcode = intcode(pos)
    val pos1 = intcode(pos+1)
    val pos2 = intcode(pos+2)
    val resPos = intcode(pos+3)
    opcode match {
      case 1 =>
        intcode(resPos) = intcode(pos1) + intcode(pos2)
        runIntcode(pos + 4, intcode)
      case 2 =>
        intcode(resPos) = intcode(pos1) * intcode(pos2)
        runIntcode(pos + 4, intcode)
      case 99 => intcode
      case _ => throw new Exception(s"Invalid opcode: $opcode")
    }
  }

  def main(args: Array[String]): Unit = {
    val intcode = input.mkString.split(",").map(_.replaceAll("\\s", "")).map(_.toInt)
    for (noun <- 0 to 99) {
      for (verb <- 0 to 99) {
        val memory = intcode.clone
        memory(1) = noun
        memory(2) = verb
        val result = runIntcode(0, memory)(0)
        if (result == targetOutput) {
          print(s"Traget output of $targetOutput was generated using noun=$noun and verb=$verb. Result: ${100*noun+verb}")
          return
        }
      }
    }
  }
}
