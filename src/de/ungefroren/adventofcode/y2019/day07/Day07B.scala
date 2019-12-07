package de.ungefroren.adventofcode.y2019.day07

import java.io.{File, InputStream}

import scala.annotation.tailrec
import scala.io.{Source, StdIn}

//This is not very clean, I just hacked my existing intcode computer to stop whenever a input is required or a output is given
object Day07B {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  class IntcodeComputer(private var _program: Array[Int]) {

    private var resume_with: Option[(Int, Option[Int])] = None
    private var _output: Option[Int] = None

    final def program: Array[Int] = _program.clone

    def resume(input: Option[Int] = None): Boolean = {
      if (resume_with.isEmpty) throw new Exception("Program isn't in halted state, cannot resume")
      val pos = resume_with.get._1
      if (resume_with.get._2.isDefined) {
        if (input.isEmpty) throw new Exception("Please supply an input")
        _program(resume_with.get._2.get) = input.get
      }
      _output = None
      resume_with = None
      run(pos)
    }

    def output: Option[Int] = _output

    @tailrec
    final def run(pos: Int = 0): Boolean = {
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
          resume_with = Some(pos + 2, Some(param(1)))
          return false
        case 4 =>
          _output = Some(_program(param(1)))
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
        case 99 => return true//End program
        case _ => throw new Exception(s"Invalid opcode: $opcode")
      }
      run(jumpto)
    }

    override def clone(): IntcodeComputer = {
      IntcodeComputer(program)
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
    val range = Set(5, 6, 7, 8, 9)
    val instruction = input.mkString.split(",").filter(_.matches("\\d+")).map(_.toInt)
    val intcodeComputer = IntcodeComputer(instruction)
    val results = (for (a <- range) yield {
      for (b <- range - a) yield {
        for (c <- range - a - b) yield {
          for (d <- range - a - b - c) yield {
            for (e <- range - a - b - c - d) yield {
              val ampA, ampB, ampC, ampD, ampE = intcodeComputer.clone()

              //Setup
              ampA.run()
              ampA.resume(Some(a))
              ampB.run()
              ampB.resume(Some(b))
              ampC.run()
              ampC.resume(Some(c))
              ampD.run()
              ampD.resume(Some(d))
              ampE.run()
              ampE.resume(Some(e))

              var exit = false
              var last: Option[Int] = Some(0)
              while (!exit) {
                ampA.resume(last)
                ampB.resume(ampA.output)
                ampC.resume(ampB.output)
                ampD.resume(ampC.output)
                exit = ampE.resume(ampD.output)
                last = ampE.output
              }
              last.get
            }
          }
        }.flatten
      }.flatten
    }).flatten.flatten
    println(s"Max signal: ${results.max}")
  }
}
