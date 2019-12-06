package de.ungefroren.adventofcode.y2019.day05

import scala.annotation.tailrec
import scala.io.{Source, StdIn}

object Day05B {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  @tailrec
  def runIntcode(pos: Int, intcode: Array[Int]): Array[Int] = {
    val opcode = intcode(pos)
    def param(index: Int): Int = {
      val mode = (opcode / math.pow(10, index+1).toInt) % 10
      if (mode == 1) {
        val i = pos+index
        i
      } else {
        val i = intcode(pos+index)
        i
      }
    }
    (opcode % 100) match {
      case 1 =>
        intcode(param(3)) = intcode(param(1)) + intcode(param(2))
        runIntcode(pos + 4, intcode)
      case 2 =>
        intcode(param(3)) = intcode(param(1))* intcode(param(2))
        runIntcode(pos + 4, intcode)
      case 3 =>
        print("> ")
        intcode(param(1)) = StdIn.readInt()
        runIntcode(pos + 2, intcode)
      case 4 =>
        println(s"[TEST]: ${intcode(param(1))}")
        runIntcode(pos + 2, intcode)
      case 5 =>
        if (intcode(param(1)) != 0) {
          runIntcode(intcode(param(2)), intcode)
        } else {
          runIntcode(pos + 3, intcode)
        }
      case 6 =>
        if (intcode(param(1)) == 0) {
          runIntcode(intcode(param(2)), intcode)
        } else {
          runIntcode(pos + 3, intcode)
        }
      case 7 =>
        if (intcode(param(1)) < intcode(param(2))) {
          intcode(param(3)) = 1
        } else {
          intcode(param(3)) = 0
        }
        runIntcode(pos + 4, intcode)
      case 8 =>
        if (intcode(param(1)) == intcode(param(2))) {
          intcode(param(3)) = 1
        } else {
          intcode(param(3)) = 0
        }
        runIntcode(pos + 4, intcode)
      case 99 => intcode
      case _ => throw new Exception(s"Invalid opcode: $opcode")
    }
  }

  def main(args: Array[String]): Unit = {
    val intcode = input.mkString.split(",").map(_.replaceAll("\\s", "")).map(_.toInt)
    runIntcode(0, intcode)
  }

}
