package de.ungefroren.adventofcode.y2019.intcodeComputer

import scala.reflect.runtime.universe._

sealed abstract class ComputationResult()

/**
 * Each operation in the intcode computer returns whether the computer should continue executing or should stop
 */
object ComputationResult {

  /**
   * Instruction requires the computer to stop executing the program and wait for something
   */
  sealed abstract class HALT() extends ComputationResult()

  /**
   * Instruction requires the computer to stop executing the program and wait for resuming
   *
   * @param output some output that was provided by the last operation
   */
  case class HALT_OUTPUT_PROVIDED(output: Int) extends HALT()

  /**
   * Program was executed successfully and will now exit
   */
  object EXIT_SUCCESSFUL extends ComputationResult()

  /**
   * Instruction requires the computer to stop executing the program and wait for input
   */
  object HALT_WAITING_FOR_INPUT extends HALT()

  /**
   * Just continue executing with the next instruction
   */
  object CONTINUE_EXECUTION extends ComputationResult()

  /**
   * Continue executing at a specific position
   *
   * @param instruction_pointer the position where th program should jump to
   */
  case class JUMPTO(instruction_pointer: Int) extends ComputationResult()

}