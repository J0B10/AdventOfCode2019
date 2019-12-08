package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, Instruction, IntcodeComputer}

/**
 * Ends the current program and returns `ComputationResult.EXIT_SUCCESSFUL`
 */
object Exit extends Instruction(99, 0) {

  override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult = ComputationResult.EXIT_SUCCESSFUL
}
