package de.ungefroren.adventofcode.y2019.day08

import java.awt.Color
import java.awt.image.BufferedImage

import javax.imageio.ImageIO
import javax.swing.{ImageIcon, JFrame, JLabel}

import scala.annotation.tailrec
import scala.io.Source

object Day08B {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  implicit class ArrayUtility[T <: Any](val array: Array[T]) {

    def splitEvery(n: Int): Seq[Array[T]] = {
      @tailrec
      def splitEvery(a: Array[T], n: Int, seq: Seq[Array[T]]): Seq[Array[T]] = {
        val a1 = a take n
        val a2 = a drop n
        if (a2.isEmpty) {
          seq :+ a1
        } else {
          splitEvery(a2, n, seq :+ a1)
        }
      }
      splitEvery(array, n, Seq())
    }
  }

  def main(args: Array[String]): Unit = {
    val digits = input.mkString.toCharArray.flatMap({case c if c.isDigit => Some(c.asDigit) case _ => None})
    val layers = digits.splitEvery(25 * 6)
    val image = new BufferedImage(27 * 50,8 * 50, BufferedImage.TYPE_INT_RGB)
    val paint = image.getGraphics
    paint.setColor(new Color(15, 15, 35))
    paint.fillRect(0,0,27*50,8*50)
    for (layer <- layers.reverse) {
      for ((row, y) <- layer.splitEvery(25).zipWithIndex) {
        for ((pixel, x) <- row.zipWithIndex if pixel!=2) {
          pixel match {
            case 0 => paint.setColor(new Color(15, 15, 35))
            case 1 => paint.setColor(new Color(204, 204, 204))
            case _ => paint.setColor(Color.RED) //should never occur
          }
          paint.fillRect(x * 50 + 50, y * 50 + 50, 50, 50)
        }
      }
    }
    paint.dispose()
    val frame = new JFrame("Space Image (Advent of Code Day 8)")
    frame.setIconImage(ImageIO.read(getClass.getResourceAsStream("/de/ungefroren/adventofcode/y2019/icon.png")))
    frame.add(new JLabel(new ImageIcon(image)))
    frame.pack()
    frame.setVisible(true)
  }

}
