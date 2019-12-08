package de.ungefroren.adventofcode.y2019.intcodeComputer

import scala.annotation.tailrec
import scala.io.Source

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
class IntcodeComputer(var memory: Array[Int])(instructions: Set[Instruction]) {

  private val instruction_map = instructions.groupBy(_.opcode).transform({
    case (_, instructions) if instructions.size == 1 => instructions.head
    case _ => throw new Exception("Each instructions opcode must be unique!")
  })

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

  /**
   * Execute the program defined in this computers memory starting at `instruction_pointer`
   *
   * @return the result with which the program stopped
   */
  @tailrec
  final def run(): ComputationResult = {
    val op = memory(instruction_pointer) % 100
    instruction_map get op match {
      case Some(instruction) =>
        val parameters = (0 until instruction.aop).map(param)
        _lastResult = Some(instruction.execute(parameters, this))
        lastResult match {
          case ComputationResult.CONTINUE_EXECUTION =>
            instruction_pointer += instruction.aop + 1
            run()
          case ComputationResult.JUMPTO(i) =>
            instruction_pointer = i
            run()
          case _ => lastResult
        }
      case _ => throw new Exception(s"Invalid opcode at $instruction_pointer: No instruction with opcode $op supported.")
    }
  }

  /**
   * Resume a program that is in HALT state (last Computation Result was `HALT`)
   *
   * @param input some optional input, required if the program stopped with `HALT_WAITING_FOR_INPUT`
   * @return the result with which the program stopped
   */
  final def resume(input: Option[Int]): ComputationResult = {
    val op = memory(instruction_pointer) % 100
    instruction_map get op match {
      case Some(instruction) =>
        instruction match {
          case resumable: Resumable =>
            val parameters = (0 until instruction.aop).map(param)
            _lastResult = Some(resumable.resume(input, parameters, this))
            lastResult match {
              case ComputationResult.CONTINUE_EXECUTION =>
                instruction_pointer += instruction.aop + 1
                run()
              case ComputationResult.JUMPTO(i) =>
                instruction_pointer = i
                run()
              case _ => lastResult
            }
          case _ => throw new Exception("Last instruction isn't resumable")
        }
      case _ => throw new Exception(s"Invalid opcode at $instruction_pointer: No instruction with opcode $op supported.")
    }
  }

  /**
   * Resume a program that stopped returning `HALT_WAITING_FOR_INPUT` and provide an input
   *
   * @param input with which the program should continue
   * @return the result with which the program stopped
   */
  final def resume(input: Int): ComputationResult = resume(Some(input))

  /**
   * Resume a program that stopped returning `HALT_OUTPUT_PROVIDED`
   *
   * @return the result with which the program stopped
   */
  final def resume(): ComputationResult = resume(None)

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
  }

  /**
   * Get the place where a parameter is stored inside the memory from its index
   *
   * @param index the number of the parameter
   * @return the parameters index inside the memory array
   */
  private final def param(index: Int): Int = {
    parameterMode(index) match {
      case 0 => memory(instruction_pointer + index + 1)
      case 1 => instruction_pointer + index + 1
      case mode => throw new Exception(s"Unknown parameter mode at $instruction_pointer for parameter $index: $mode")
    }
  }
}

object IntcodeComputer {
  def apply(memory: Array[Int])(implicit instructions: Set[Instruction]): IntcodeComputer =
    new IntcodeComputer(memory)(instructions)

  def apply(source: Source)(implicit instructions: Set[Instruction]): IntcodeComputer =
    new IntcodeComputer(source.mkString.replaceAll("\\s", "").split(",").map(_.toInt))(instructions)
}
