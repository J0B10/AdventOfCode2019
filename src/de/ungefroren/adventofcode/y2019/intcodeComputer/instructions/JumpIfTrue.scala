package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, Instruction, IntcodeComputer}

/**
 * If the first parameter non-zero, jumps to the position defined by the second parameter
 */
object JumpIfTrue extends Instruction(5, 2) {

  override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult = {
    if (computer.memory(param.head) != 0) {
      ComputationResult.JUMPTO(computer.memory(param(1)))
    } else {
      ComputationResult.CONTINUE_EXECUTION
    }
  }
}
