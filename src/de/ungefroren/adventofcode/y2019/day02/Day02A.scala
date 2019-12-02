package de.ungefroren.adventofcode.y2019.day02

import scala.annotation.tailrec
import scala.io.Source

object Day02A {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

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
    intcode(1) = 12
    intcode(2) = 2
    val result = runIntcode(0,intcode)(0)
    print(s"Value at position 0 is: $result")
  }

}
