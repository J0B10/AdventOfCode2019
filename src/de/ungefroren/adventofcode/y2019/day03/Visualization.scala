package de.ungefroren.adventofcode.y2019.day03

import java.awt.{Color, Dimension, Graphics, Point}

import javax.imageio.ImageIO
import javax.swing.{JFrame, JPanel, JScrollPane, UIManager}

import scala.collection.mutable.ListBuffer

object Visualization {

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  val BACKGROUND_COLOR = new Color(15, 15, 35)
  val ACCENT_1_COLOR = new Color(0, 153, 0)
  val ACCENT_2_COLOR = new Color(204, 204, 204)

  private val frame = new JFrame("Wires (Advent of Code Day 3)")
  private val scrollPane = new JScrollPane(Canvas)

  frame setIconImage ImageIO.read(getClass.getResource("/de/ungefroren/adventofcode/y2019/icon.png"))
  frame setPreferredSize new Dimension(500, 500)
  frame setMinimumSize new Dimension(200, 200)
  frame add scrollPane

  def canvasSize(width: Int, height: Int): Unit = {
    val dim = new Dimension(width, height)
    Canvas setMinimumSize dim
    Canvas setPreferredSize dim
    Canvas setMaximumSize dim
    frame.pack()
    val size = scrollPane.getViewport.getExtentSize
    scrollPane.getViewport setViewPosition new Point((width - size.width) / 2, (height - size.height) / 2)
    Canvas.center = new Point(width / 2, height / 2)
  }

  def wire1(line: ((Int, Int), (Int, Int))): Unit = {
    Canvas.wire1 += Canvas.adjustToCenter(line)
  }

  def wire2(line: ((Int, Int), (Int, Int))): Unit = {
    Canvas.wire2 += Canvas.adjustToCenter(line)
  }

  def display(): Unit = {
    frame setVisible true
  }

  private object Canvas extends JPanel {

    var center = new Point(0, 0)

    val wire1: ListBuffer[((Int, Int), (Int, Int))] = ListBuffer()
    val wire2: ListBuffer[((Int, Int), (Int, Int))] = ListBuffer()

    setBackground(Visualization.BACKGROUND_COLOR)

    def adjustToCenter(line: ((Int, Int), (Int, Int))): ((Int, Int), (Int, Int)) = {
      ((line._1._1 + center.x, line._1._2 + center.y), (line._2._1 + center.x, line._2._2 + center.y))
    }

    override def paintComponent(g: Graphics): Unit = {
      super.paintComponent(g)
      g setColor Color.yellow
      g.drawRect(center.x - 5, center.y - 5, 10, 10)
      g setColor ACCENT_1_COLOR
      wire1 foreach (l => g.drawLine(l._1._1, l._1._2, l._2._1, l._2._2))
      g setColor ACCENT_2_COLOR
      wire2 foreach (l => g.drawLine(l._1._1, l._1._2, l._2._1, l._2._2))
    }
  }

}
