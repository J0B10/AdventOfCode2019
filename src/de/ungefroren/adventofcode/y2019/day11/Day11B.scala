package de.ungefroren.adventofcode.y2019.day11

import java.awt.Color
import java.awt.image.BufferedImage

import de.ungefroren.adventofcode.y2019.day08.Day08B.getClass
import de.ungefroren.adventofcode.y2019.day11.Day11A.Facing.Up
import de.ungefroren.adventofcode.y2019.intcodeComputer.IntcodeComputer
import de.ungefroren.adventofcode.y2019.intcodeComputer.instructions._
import javax.imageio.ImageIO
import javax.swing.{ImageIcon, JFrame, JLabel}

import scala.collection.mutable
import scala.io.Source

object Day11B {
  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  val painted = mutable.Map[Position, Int](Position(0,0) -> 1)

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

    showPainted()
  }

  def paintedAreaSize: (Int, Int) = {
    var (sizeX, sizeY) = (1,1)
    for (pos <- painted.keys) {
      val dx = math.abs(pos.x) * 2
      val dy = math.abs(pos.y) * 2
      if (dx > sizeX) sizeX = dx + 1
      if (dy > sizeY) sizeY = dy + 1
    }
    (sizeX, sizeY)
  }

  def showPainted(): Unit = {
    val (width, height) = paintedAreaSize
    val image = new BufferedImage((width+2) * 10, (height+2) * 10, BufferedImage.TYPE_INT_RGB)
    val paint = image.getGraphics
    paint.setColor(new Color(15, 15, 35))
    paint.fillRect(0,0, image.getWidth, image.getHeight)

    for ((pos, c) <- painted) {
      if (c == 1) paint.setColor(Color.WHITE) else paint.setColor(Color.BLACK)
      paint.fillRect(image.getWidth / 2 + pos.x * 10 + 10, image.getHeight / 2 - pos.y * 10 - 10, 10, 10)
    }

    paint.dispose()
    val frame = new JFrame("Hull (Advent of Code Day 11 B)")
    frame.setIconImage(ImageIO.read(getClass.getResourceAsStream("/de/ungefroren/adventofcode/y2019/icon.png")))
    frame.add(new JLabel(new ImageIcon(image)))
    frame.pack()
    frame.setVisible(true)
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
    private[PaintingRobot] var _facing: Facing = Facing.Up

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
