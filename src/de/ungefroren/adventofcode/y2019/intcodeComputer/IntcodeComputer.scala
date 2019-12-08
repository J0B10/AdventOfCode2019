package de.ungefroren.adventofcode.y2019.intcodeComputer

import de.ungefroren.adventofcode.y2019.intcodeComputer.instructions._

import scala.annotation.tailrec

/**
 * A intcode computer runs a program that is defined only by integers.
 *
 * @param memory The memory contains the instructions for the program and space for variables.
 *               It is altered by the computer while the program runs.
 *
 *               '''Attention:'''
 *               Be careful with changing this while the computer is running or in HALT state.
 *               It could introduce unintended behaviour.
 * @param instructions
 *               The instructions supported by this intcode computer.
 *               Import `instructions.defaults` to use the default set of instructions for your computer
 *               or define your own ones
 */
class IntcodeComputer(var memory: Array[Int])(implicit val instructions: Seq[Instruction]) {

  /**
   * The address of the instruction inside the memory where the computer should proceed executing once `run()` is called
   *
   * '''Attention:'''
   * Be careful with changing this while the computer is running or in HALT state
   * It could introduce unintended behaviour.
   */
  var instruction_pointer = 0

  private var _lastResult: Option[ComputationResult] = None

  /**
   * @return the result of the last instruction (throws an NoSuchElementException if this computer did never run)
   */
  def lastResult: ComputationResult = _lastResult.get

  @tailrec
  final def run(): ComputationResult = {
    val op = memory(instruction_pointer) % 100
    instructions find (_.opcode == op) match {
      case Some(instruction) =>
        val parameters = (1 to instruction.aop).map(param).toSeq
        _lastResult = Some(instruction.execute(parameters, this))
        lastResult match {
          case ComputationResult.CONTINUE_EXECUTION =>
            instruction_pointer += instruction.aop + 1
            run()
          case _ => lastResult
        }
      case _ => throw new Exception(s"Invalid opcode at $instruction_pointer: No instruction with opcode $op supported.")
    }
  }

  /**
   * Determine how the instruction parameters should be handled.
   *
   * {{{
   *   0 = position mode
   *   1 = immediate mode
   * }}}
   *
   * @param parameter the index of the parameter
   * @return the mode for the given parameter
   */
  private final def parameterMode(parameter: Int): Int = {
    val opcode = memory(instruction_pointer)
    (opcode / math.pow(10, parameter + 2).toInt) % 10
    memory.min
  }

  /**
   * A parameters must be in position mode if you want to write to its position in the memory.
   *
   * This method checks for it and throws an exception otherwise
   *
   * @param index the number of the parameter
   */
  private[intcodeComputer] final def checkWrite(index: Int): Unit = {
    parameterMode(index) match {
      case 0 => //Parameter in position mode, do nothing
      case mode => throw new Exception(s"Can't write to parameter $index at position $instruction_pointer: Parameter mode is $mode")
    }
  }

  /**
   * Get the place where a parameter is stored inside the memory from its index
   *
   * @param index the number of the parameter
   * @return the parameters index inside the memory array
   */
  private final def param(index: Int): Int = {
    parameterMode(index) match {
      case 0 => memory(instruction_pointer + index)
      case 1 => instruction_pointer + index
      case mode => throw new Exception(s"Unknown parameter mode at $instruction_pointer for parameter $index: $mode")
    }
  }
}
