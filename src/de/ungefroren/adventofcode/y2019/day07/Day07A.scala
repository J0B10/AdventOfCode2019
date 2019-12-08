package de.ungefroren.adventofcode.y2019.day07


import de.ungefroren.adventofcode.y2019.intcodeComputer.{Instruction, IntcodeComputer}
import de.ungefroren.adventofcode.y2019.intcodeComputer.instructions._

import scala.io.{Source}

object Day07A {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  val instructions: Set[Instruction] = defaults - InputStdIn - OutputStdOut

  def main(args: Array[String]): Unit = {
    val range = Set(0, 1, 2, 3, 4)
    val instruction = input.mkString.split(",").filter(_.matches("\\d+")).map(_.toInt)
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

              val ampA_instructions = instructions + InputListener(() => input(a)) + OutputListener(i => last = i)
              val ampB_instructions = instructions + InputListener(() => input(b)) + OutputListener(i => last = i)
              val ampC_instructions = instructions + InputListener(() => input(c)) + OutputListener(i => last = i)
              val ampD_instructions = instructions + InputListener(() => input(d)) + OutputListener(i => last = i)
              val ampE_instructions = instructions + InputListener(() => input(e)) + OutputListener(i => last = i)

              val ampA = IntcodeComputer(instruction)(ampA_instructions)
              val ampB = IntcodeComputer(instruction)(ampB_instructions)
              val ampC = IntcodeComputer(instruction)(ampC_instructions)
              val ampD = IntcodeComputer(instruction)(ampD_instructions)
              val ampE = IntcodeComputer(instruction)(ampE_instructions)

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
