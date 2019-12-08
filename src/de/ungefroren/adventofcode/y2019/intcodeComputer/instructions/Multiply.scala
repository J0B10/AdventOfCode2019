package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, Instruction, IntcodeComputer}

/**
 * Multiplies two parameters and saves the result to the address defined by the third parameter
 */
object Multiply extends Instruction(2,3){

  override def execute(param: Seq[Int], computer:IntcodeComputer): ComputationResult = {
    val memory = computer.memory
    computer.checkWrite(2)
    memory(param(2)) = memory(param.head) * memory(param(1))
    ComputationResult.CONTINUE_EXECUTION
  }
}
