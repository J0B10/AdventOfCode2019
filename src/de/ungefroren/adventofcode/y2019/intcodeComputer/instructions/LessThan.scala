package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, Instruction, IntcodeComputer}

/**
 * Compares first and second parameter.
 * If th first is less than the second it stores 1, otherwise 0 át the position defined by the third parameter.
 */
object LessThan extends Instruction(7,3) {

  override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult = {
    val i = if (computer.memory(param.head) < computer.memory(param(1))) {
      1
    } else {
      0
    }
    computer.checkWrite(2)
    computer.memory(param(2)) = i
    ComputationResult.CONTINUE_EXECUTION
  }
}
