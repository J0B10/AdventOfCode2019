package de.ungefroren.adventofcode.y2019.day10

import scala.collection.mutable
import scala.io.Source

object Day10A {
  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  def main(args: Array[String]): Unit = {
    val asteroids = parseAsteroids(input.getLines().toSeq)
    val max_visible = asteroids.map(asteroid => {
      (asteroid, visibleAsteroids(asteroid, asteroids).size)
    }).maxBy(_._2)
    println(s"From ${max_visible._1} are ${max_visible._2} asteroids visible.")
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

  def parseAsteroids(lines: Seq[String]): Seq[Asteroid] = {
    val asteroids = mutable.Buffer[Asteroid]()
    for ((line, y) <- lines.zipWithIndex) {
      for ((char, x) <- line.toCharArray.zipWithIndex) {
        if (char == '#') {
          asteroids += Asteroid(x, y)
        }
      }
    }
    asteroids.toSeq
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
  }

}
