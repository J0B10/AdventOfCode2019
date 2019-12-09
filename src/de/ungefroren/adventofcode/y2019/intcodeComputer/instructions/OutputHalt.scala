package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, IntcodeComputer, Resumable}

/**
 * Stops the execution of the intcode computer and provides an output
 *
 * Usefull for using multiple intcode computers parallel
 */
object OutputHalt extends Output with Resumable {

  override def resume(input: Option[Int], param: Seq[Int], computer: IntcodeComputer): ComputationResult =
    ComputationResult.CONTINUE_EXECUTION

  override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult =
    ComputationResult.HALT_OUTPUT_PROVIDED(computer.memory(param.head).toInt)
}
