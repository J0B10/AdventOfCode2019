package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions
import de.ungefroren.adventofcode.y2019.intcodeComputer.{ComputationResult, IntcodeComputer}

import scala.io.StdIn

/**
 * Read in some input from StdIn
 */
object InputStdIn extends Input {
  override def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult = {
    computer.checkWrite(param.head)
    print("> ")
    computer.memory(param.head) = StdIn.readInt()
    ComputationResult.CONTINUE_EXECUTION
  }
}
