package de.ungefroren.adventofcode.y2019.intcodeComputer

/**
 * An instruction defines what operation should be done for a specific opcode.
 *
 * To execute the operation call `execute()`
 *
 * @param opcode opcode for which this operation should be called
 * @param aop    amount of parameters used by this instruction
 */
abstract class Instruction(val opcode: Int, val aop: Int) {

  /**
   * Execute this operation
   *
   * @param param the positions in the memory where the parameters are stored
   * @param computer the intcode computer that performs this operation
   */
  def execute(param: Seq[Int], computer: IntcodeComputer): ComputationResult

}
object Instruction {
  def unapply(arg: Instruction): Option[(Int, Int)] = Some(arg.opcode, arg.aop)
}
