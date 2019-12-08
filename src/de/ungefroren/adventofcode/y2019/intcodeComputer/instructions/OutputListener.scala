package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, IntcodeComputer}

/**
 * Execute a function that handles any output of the intcode computer
 *
 * @param onOutput function that receives the output
 */
case class OutputListener(onOutput: Int => Unit) extends Output {

  final override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult = {
    onOutput(computer.memory(param.head))
    ComputationResult.CONTINUE_EXECUTION
  }
}
