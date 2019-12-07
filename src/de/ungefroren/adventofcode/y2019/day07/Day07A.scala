package de.ungefroren.adventofcode.y2019.day07

import java.io.{File, InputStream}

import scala.annotation.tailrec
import scala.io.{Source, StdIn}

object Day07A {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  class IntcodeComputer(private var _program: Array[Int]) {

    final def program: Array[Int] = _program.clone

    var output: Int => Unit = out => println(s"[Test]: $out")

    var input: () => Int = () => {
      print("> ")
      StdIn.readInt()
    }

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
          _program(param(1)) = input()
          pos + 2
        case 4 =>
          output(_program(param(1)))
          pos + 2
        case 5 =>
          if (_program(param(1)) != 0) {
            _program(param(2))
          } else {
            pos + 3
          }
        case 6 =>
          if (_program(param(1)) == 0) {
            _program(param(2))
          } else {
            pos + 3
          }
        case 7 =>
          if (_program(param(1)) < _program(param(2))) {
            _program(param(3)) = 1
          } else {
            _program(param(3)) = 0
          }
          pos + 4
        case 8 =>
          if (_program(param(1)) == _program(param(2))) {
            _program(param(3)) = 1
          } else {
            _program(param(3)) = 0
          }
          pos + 4
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
    val range = Set(0, 1, 2, 3, 4)
    val instruction = input.mkString.split(",").filter(_.matches("\\d+")).map(_.toInt)
    val ampA, ampB, ampC, ampD, ampE = IntcodeComputer(instruction.clone())
    val results = (for (a <- range) yield {
      for (b <- range - a) yield {
        for (c <- range - a - b) yield {
          for (d <- range - a - b - c) yield {
            for (e <- range - a - b - c - d) yield {
              var last = 0
              var inputLast = false

              def input(first: Int): Int = {
                if (inputLast) {
                  inputLast = !inputLast
                  last
                } else {
                  inputLast = !inputLast
                  first
                }
              }

              ampA.output = i => last = i
              ampB.output = i => last = i
              ampC.output = i => last = i
              ampD.output = i => last = i
              ampE.output = i => last = i

              ampA.input = () => input(a)
              ampB.input = () => input(b)
              ampC.input = () => input(c)
              ampD.input = () => input(d)
              ampE.input = () => input(e)

              ampA.run()
              ampB.run()
              ampC.run()
              ampD.run()
              ampE.run()
              last
            }
          }
        }.flatten
      }.flatten
    }).flatten.flatten
    println(s"Max signal: ${results.max}")
  }
}
