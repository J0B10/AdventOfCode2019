package de.ungefroren.adventofcode.y2019.intcodeComputer

/**
 *  An instruction that may return a computation result on execute that will cause the computer to stop and wait for input.
 *  If the input is supplied `resume()` is called to continue with the execution
 */
trait Resumable {
  
  def resume(input: Array[Int], memory: Array[Int]): ComputationResult

}
