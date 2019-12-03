package de.ungefroren.adventofcode.y2019.day03

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day03A {

  val REGEX_UP = "U(\\d+)".r
  val REGEX_DOWN = "D(\\d+)".r
  val REGEX_LEFT = "L(\\d+)".r
  val REGEX_RIGHT = "R(\\d+)".r

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  case class Point(x: Int, y: Int) {
    def isBetween(pointA: Point, pointB: Point): Boolean = {
      if (x == pointA.x && x == pointB.x) {
        (pointA.y < y && y < pointB.y) || (pointA.y > y && y > pointB.y)
      } else if (y == pointA.y && y == pointB.y) {
        (pointA.x < x && x < pointB.x) || (pointA.x > x && x > pointB.x)
      } else {
        false
      }
    }
  }

  case class WireStrip(start: Point, end: Point) {

    def vertical: Boolean = start.x == end.x

    def horizontal: Boolean = start.y == end.y

    def intersection(wireStraight: WireStrip): Option[Point] = {
      if (vertical == wireStraight.vertical || horizontal == wireStraight.horizontal) {
        None
      } else {
        val intersect =
          if (vertical && wireStraight.horizontal) {
            Point(start.x, wireStraight.start.y)
          } else if (horizontal && wireStraight.vertical) {
            Point(wireStraight.start.x, start.y)
          } else {
            throw new Exception("Wires are not in Manhattan grid")
          }
        if (intersect.isBetween(start, end) && intersect.isBetween(wireStraight.start, wireStraight.end)) {
          Some(intersect)
        } else {
          None
        }
      }
    }
  }

  def followWire(wire: Array[String]): List[WireStrip] = {
    val path: ListBuffer[WireStrip] = ListBuffer()

    def last: Point = path.lastOption.map(_.end).getOrElse(Point(0, 0))

    wire foreach {
      case REGEX_UP(steps) =>
        path += WireStrip(last, Point(last.x, last.y - steps.toInt))
      case REGEX_DOWN(steps) =>
        path += WireStrip(last, Point(last.x, last.y + steps.toInt))
      case REGEX_LEFT(steps) =>
        path += WireStrip(last, Point(last.x - steps.toInt, last.y))
      case REGEX_RIGHT(steps) =>
        path += WireStrip(last, Point(last.x + steps.toInt, last.y))
      case _ => throw new Exception("Invalid instruction")
    }
    path.toList
  }

  def main(args: Array[String]): Unit = {
    val lines = input.getLines().filterNot(_.matches("\\s*")).toList
    val wire1 = lines.head.split(",")
    val wire2 = lines(1).split(",")
    val wire1_strips = followWire(wire1)
    println(wire1_strips)
    val wire2_strips = followWire(wire2)
    println(wire2_strips)
    val intersections = wire1_strips.flatMap(strip1 => wire2_strips.flatMap(strip2 => strip2.intersection(strip1)))
    println(intersections)
    println(s"Distance: ${intersections.map(p => math.abs(p.x) + math.abs(p.y)).min}")
  }
}
