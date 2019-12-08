package de.ungefroren.adventofcode.y2019.intcodeComputer

/**
 * Instructions that implement this trait may cause the intode computer to stop and wait till it is resumed.
 *
 * If the intcode computer is resumed `resume()` is called to continue with the execution
 */
trait Resumable {

  /**
   * Resume the current operation
   * @param input some input if it was provided or none
   * @param param    the positions in the memory where the parameters for this instruction are stored
   * @param computer the intcode computer that performs this operation
   * @return the result of this computation
   */
  def resume(input: Option[Int], param: Seq[Int], computer: IntcodeComputer): ComputationResult

}
