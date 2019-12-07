package de.ungefroren.adventofcode.y2019.day05

import java.io.{File, InputStream}

import scala.annotation.tailrec
import scala.io.{Source, StdIn}

object Day05A {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  class IntcodeComputer(private var _program: Array[Int]) {

    final def program: Array[Int] = _program.clone

    @tailrec
    final def run(pos: Int = 0): Unit = {
      val opcode = _program(pos)

      def param(index: Int): Int = {
        val mode = (opcode / math.pow(10, index + 1).toInt) % 10
        if (mode == 1) {
          val i = pos + index
          i
        } else {
          val i = _program(pos + index)
          i
        }
      }

      val jumpto = (opcode % 100) match {
        case 1 =>
          _program(param(3)) = _program(param(1)) + _program(param(2))
          pos + 4
        case 2 =>
          _program(param(3)) = _program(param(1)) * _program(param(2))
          pos + 4
        case 3 =>
          print("> ")
          _program(param(1)) = StdIn.readInt()
          pos + 2
        case 4 =>
          println(s"[TEST]: ${_program(param(1))}")
          pos + 2
        case 99 => return //End program
        case _ => throw new Exception(s"Invalid opcode: $opcode")
      }
      run(jumpto)
    }
  }

  object IntcodeComputer {
    def apply(_program: Array[Int]): IntcodeComputer = new IntcodeComputer(_program)

    def apply(source: Source): IntcodeComputer = new IntcodeComputer(
      source.mkString.split(",").filter(_.matches("\\s+")).map(_.toInt)
    )

    def apply(file: File): IntcodeComputer = IntcodeComputer(Source.fromFile(file))

    def apply(inputStream: InputStream): IntcodeComputer = IntcodeComputer(Source.fromInputStream(inputStream))
  }

  def main(args: Array[String]): Unit = {
    IntcodeComputer(input).run()
  }

}
