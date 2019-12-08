package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, IntcodeComputer, Resumable}

/**
 * Stops the execution of the intcode computer and waits for some input
 *
 * Usefull for using multiple intcode computers parallel
 */
object InputHalt extends Input with Resumable {

  override def resume(input: Option[Int], param: Seq[Int], computer: IntcodeComputer): ComputationResult = {
    if (input.isEmpty) throw new Exception("An input must be provided!")
    computer.checkWrite(param.head)
    computer.memory(param.head) = input.get
    ComputationResult.CONTINUE_EXECUTION
  }

  override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult =
    ComputationResult.HALT_WAITING_FOR_INPUT
}
