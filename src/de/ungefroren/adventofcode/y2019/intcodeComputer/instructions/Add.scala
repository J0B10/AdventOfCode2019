package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, Instruction, IntcodeComputer}

/**
 * Adds two parameters and saves the result to the address defined by the third parameter
 */
object Add extends Instruction(1, 3) {

  override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult = {
    val memory = computer.memory
    computer.checkWrite(2)
    memory(param(2)) = memory(param.head) + memory(param(1))
    ComputationResult.CONTINUE_EXECUTION
  }
}
