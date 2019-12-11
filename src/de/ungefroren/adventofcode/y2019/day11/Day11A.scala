package de.ungefroren.adventofcode.y2019.day11

import de.ungefroren.adventofcode.y2019.day11.Day11A.Facing.Up
import de.ungefroren.adventofcode.y2019.intcodeComputer.IntcodeComputer
import de.ungefroren.adventofcode.y2019.intcodeComputer.instructions._

import scala.collection.mutable
import scala.io.Source

object Day11A {
  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  val painted = mutable.Map[Position, Int]()

  def main(args: Array[String]): Unit = {
    val instructions = (
      defaults
        - InputStdIn
        - OutputStdOut
        + InputListener(ComputerIO.input)
        + OutputListener(ComputerIO.output)
      )
    val computer = IntcodeComputer(input)(instructions)

    computer.run()

    println(s"Painted ${painted.size} panels at least once.")
  }

  sealed class Facing

  case class Position(x: Int, y: Int) {
    def up(amount: Int): Position = Position(x, y + amount)

    def right(amount: Int): Position = Position(x + amount, y)

    def down(amount: Int): Position = Position(x, y - amount)

    def left(amount: Int): Position = Position(x - amount, y)
  }

  object ComputerIO {
    private[ComputerIO] var i = 0

    def input(): Int = PaintingRobot.cam()

    def output(value: Int): Unit = {
      if (!(0 to 1 contains value)) throw new Exception(s"Invalid value: $value")
      i match {
        case 0 =>
          PaintingRobot.paint(value)
          i += 1
        case _ =>
          if (value == 1) PaintingRobot.turnRight() else PaintingRobot.turnLeft()
          i = 0
      }
    }
  }

  object Facing {

    object Up extends Facing

    object Right extends Facing

    object Down extends Facing

    object Left extends Facing

  }

  object PaintingRobot {
    private[PaintingRobot] var _pos: Position = Position(0, 0)
    private[PaintingRobot] var _facing: Facing = Up

    def turnLeft(): Unit = {
      facing match {
        case Facing.Up =>
          _facing = Facing.Left
          _pos = _pos left 1
        case Facing.Right =>
          _facing = Facing.Up
          _pos = _pos up 1
        case Facing.Down =>
          _facing = Facing.Right
          _pos = _pos right 1
        case Facing.Left =>
          _facing = Facing.Down
          _pos = _pos down 1
      }
    }

    def facing: Facing = _facing

    def turnRight(): Unit = {
      facing match {
        case Facing.Up =>
          _facing = Facing.Right
          _pos = _pos right 1
        case Facing.Right =>
          _facing = Facing.Down
          _pos = _pos down 1
        case Facing.Down =>
          _facing = Facing.Left
          _pos = _pos left 1
        case Facing.Left =>
          _facing = Facing.Up
          _pos = _pos up 1
      }
    }

    def paint(color: Int): Unit = painted update(pos, color)

    def cam(): Int = painted.getOrElse(pos, 0)

    def pos: Position = _pos
  }

}
