package de.ungefroren.adventofcode.y2019.day03

import java.awt._
import java.awt.event.{MouseEvent, MouseWheelEvent}

import javax.imageio.ImageIO
import javax.swing._
import javax.swing.event.MouseInputAdapter

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
  val coords = new JLabel("10|50")
  coords.setForeground(Color.red)

  Canvas addMouseListener MouseControls
  Canvas addMouseMotionListener MouseControls
  Canvas addMouseWheelListener MouseControls
  Canvas setCursor Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)

  scrollPane setWheelScrollingEnabled false

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

  def move(dx: Int, dy: Int): Unit = {
    scrollPane.getViewport.getViewPosition translate(dx, dy)
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

    var zoomLevel = 1.0

    var center = new Point(0, 0)

    val wire1: ListBuffer[((Int, Int), (Int, Int))] = ListBuffer()
    val wire2: ListBuffer[((Int, Int), (Int, Int))] = ListBuffer()

    setBackground(Visualization.BACKGROUND_COLOR)

    def adjustToCenter(line: ((Int, Int), (Int, Int))): ((Int, Int), (Int, Int)) = {
      ((line._1._1 + center.x, line._1._2 + center.y), (line._2._1 + center.x, line._2._2 + center.y))
    }

    override def paintComponent(g: Graphics): Unit = {
      super.paintComponent(g)
      val graphics = g.asInstanceOf[Graphics2D]

      if (zoomLevel != 1) {
        graphics scale(zoomLevel, zoomLevel)
      }

      graphics setColor Color.yellow
      graphics drawRect(center.x - 5, center.y - 5, 10, 10)
      graphics setColor ACCENT_1_COLOR
      wire1 foreach (l => graphics.drawLine(l._1._1, l._1._2, l._2._1, l._2._2))
      graphics setColor ACCENT_2_COLOR
      wire2 foreach (l => graphics.drawLine(l._1._1, l._1._2, l._2._1, l._2._2))
    }
  }

  private object MouseControls extends MouseInputAdapter {
    var mouse_pressed: Boolean = false
    var start_drag: Point = _

    override def mousePressed(e: MouseEvent): Unit = {
      start_drag = e.getPoint
    }

    override def mouseDragged(e: MouseEvent): Unit = {
      val dx = e.getPoint.x - start_drag.x
      val dy = e.getPoint.y - start_drag.y
      val p = scrollPane.getViewport.getViewPosition
      p.translate(-dx, -dy)
      scrollPane.getViewport setViewPosition p
    }

    override def mouseWheelMoved(e: MouseWheelEvent): Unit = {
      val rotation = e.getWheelRotation
      if (rotation < 0) {
        Canvas.zoomLevel = Canvas.zoomLevel * 1.25
      } else {
        Canvas.zoomLevel = Canvas.zoomLevel * 0.8
      }
      Canvas.repaint()
    }
  }

}
