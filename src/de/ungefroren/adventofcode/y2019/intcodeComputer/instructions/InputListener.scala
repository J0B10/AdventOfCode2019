package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, IntcodeComputer}

/**
 * Read in a value by calling a function and write it to the memory at the given position
 *
 * May be used to provide the Intcode computer with input values programmatically
 *
 * @param provideInput function that returns an input
 */
case class InputListener(provideInput: () => Int) extends Input {

  final override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult = {
    computer.checkWrite(param.head)
    computer.memory(param.head) = provideInput()
    ComputationResult.CONTINUE_EXECUTION
  }
}
