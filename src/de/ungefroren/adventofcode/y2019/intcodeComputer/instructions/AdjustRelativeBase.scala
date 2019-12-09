package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, Instruction, IntcodeComputer}

object AdjustRelativeBase extends Instruction(9, 1) {

  override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult = {
    computer.relative_base += computer.memory(param.head).toInt
    ComputationResult.CONTINUE_EXECUTION
  }
}
