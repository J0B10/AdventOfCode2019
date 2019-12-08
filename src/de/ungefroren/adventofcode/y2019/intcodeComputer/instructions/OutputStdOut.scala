package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions
import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, IntcodeComputer}

/**
 * Print the output to the standard output (e.g. the Console)
 */
object OutputStdOut extends Output {
  override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult = {
    println(s"[ICC]: ${computer.memory(param.head)}")
    ComputationResult.CONTINUE_EXECUTION
  }
}
