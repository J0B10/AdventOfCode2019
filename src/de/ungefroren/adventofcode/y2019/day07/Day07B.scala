package de.ungefroren.adventofcode.y2019.day07

import de.ungefroren.adventofcode.y2019.intcodeComputer.instructions._
import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, Instruction, IntcodeComputer}

import scala.io.Source

object Day07B {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  val instructions: Set[Instruction] = defaults - InputStdIn - OutputStdOut

  def main(args: Array[String]): Unit = {
    val range = Set(5, 6, 7, 8, 9)
    val instruction = input.mkString.split(",").filter(_.matches("\\d+")).map(_.toInt)
    val results = (for (a <- range) yield {
      for (b <- range - a) yield {
        for (c <- range - a - b) yield {
          for (d <- range - a - b - c) yield {
            for (e <- range - a - b - c - d) yield {

              val ampA_instructions = instructions + InputHalt + OutputHalt
              val ampB_instructions = instructions + InputHalt + OutputHalt
              val ampC_instructions = instructions + InputHalt + OutputHalt
              val ampD_instructions = instructions + InputHalt + OutputHalt
              val ampE_instructions = instructions + InputHalt + OutputHalt

              val ampA = IntcodeComputer(instruction)(ampA_instructions)
              val ampB = IntcodeComputer(instruction)(ampB_instructions)
              val ampC = IntcodeComputer(instruction)(ampC_instructions)
              val ampD = IntcodeComputer(instruction)(ampD_instructions)
              val ampE = IntcodeComputer(instruction)(ampE_instructions)
              //Setup
              ampA.run()
              ampA.resume(a)
              ampB.run()
              ampB.resume(b)
              ampC.run()
              ampC.resume(c)
              ampD.run()
              ampD.resume(d)
              ampE.run()
              ampE.resume(e)

              var exit = false
              var last = 0
              while (!exit) {
                ampA.resume(last) match {
                  case ComputationResult.HALT_OUTPUT_PROVIDED(out) =>
                    last = out
                    ampA.resume()
                  case _ => //Should not occur
                }
                ampB.resume(last) match {
                  case ComputationResult.HALT_OUTPUT_PROVIDED(out) =>
                    last = out
                    ampB.resume()
                  case _ => //Should not occur
                }
                ampC.resume(last) match {
                  case ComputationResult.HALT_OUTPUT_PROVIDED(out) =>
                    last = out
                    ampC.resume()
                  case _ => //Should not occur
                }
                ampD.resume(last) match {
                  case ComputationResult.HALT_OUTPUT_PROVIDED(out) =>
                    last = out
                    ampD.resume()
                  case _ => //Should not occur
                }
                ampE.resume(last) match {
                  case ComputationResult.HALT_OUTPUT_PROVIDED(out) =>
                    last = out
                    exit = ampE.resume() match {
                      case ComputationResult.EXIT_SUCCESSFUL => true
                      case _ => false
                    }
                  case _ => //Should not occur
                }
              }
              last
            }
          }
          }.flatten
        }.flatten
    }).flatten.flatten
    println(s"Max signal: ${results.max}")
  }
}
