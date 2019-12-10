package de.ungefroren.adventofcode.y2019.day10

import scala.collection.mutable
import scala.io.Source

object Day10B {
  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  val vaporizing_station = Asteroid(28, 29)

  def main(args: Array[String]): Unit = {
    var i = 0
    var asteroids: mutable.Buffer[Asteroid] = parseAsteroids(input.getLines().toSeq).toBuffer
    var visible: Seq[Asteroid] = Seq()

    while (true) {
      asteroids --= visible
      visible = visibleAsteroids(vaporizing_station, asteroids.toSeq).sortBy(vaporizing_station.angle)
      if (i + visible.size >= 200) {
        println(visible(199-i))
        return
      }
      i += visible.size
    }
  }

  def parseAsteroids(lines: Seq[String]): List[Asteroid] = {
    val asteroids = mutable.Buffer[Asteroid]()
    for ((line, y) <- lines.zipWithIndex) {
      for ((char, x) <- line.toCharArray.zipWithIndex) {
        if (char == '#') {
          asteroids += Asteroid(x, y)
        }
      }
    }
    asteroids.toList
  }

  def visibleAsteroids(from: Asteroid, asteroids: Seq[Asteroid]): Seq[Asteroid] = {
    val visible = mutable.ListBuffer[Asteroid]()
    asteroids.sortBy(-from.distanceSquared(_)).tail.foreach(a => {
      if (visible.isEmpty || !visible.exists(b => from.inLine(a, b))) {
        visible += a
      }
    })
    visible.toSeq
  }

  case class Asteroid(x: Int, y: Int) {

    def distanceSquared(b: Asteroid): Int = {
      val (dx, dy) = (math.abs(x - b.x), math.abs(y - b.y))
      dx * dx + dy * dy
    }

    def inLine(b: Asteroid, c: Asteroid): Boolean = {
      if ((b.x > x) == (c.x > x) && (b.y > y) == (c.y > y)) {
        tanOfAngle(b) == tanOfAngle(c)
      } else {
        false
      }
    }

    def tanOfAngle(b: Asteroid): Float = {
      val (dx, dy) = (math.abs(x - b.x), math.abs(y - b.y))
      dy.toFloat / dx.toFloat
    }

    def angle(b: Asteroid): Float = {
      if (b.x > x && b.y >= y) {
        (math.Pi * 0.5 - math.atan(tanOfAngle(b))).toFloat
      } else if (b.x < x && b.y >= y) {
        (math.Pi * 1.5 + math.atan(tanOfAngle(b))).toFloat
      } else if (b.x < x && b.y < y) {
        (math.Pi * 1.5 - math.atan(tanOfAngle(b))).toFloat
      } else if (b.x > x && b.y < y) {
        (math.Pi * 0.5 + math.atan(tanOfAngle(b))).toFloat
      } else if (b.y >= y) {
        0
      } else {
        math.Pi.toFloat
      }
    }
  }
}
