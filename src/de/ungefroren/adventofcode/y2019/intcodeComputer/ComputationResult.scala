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
   * Some Output is provided
   *
   */
  case class HALT_OUTPUT_PROVIDED[T: TypeTag](output: T) extends HALT()

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

}