package de.ungefroren.adventofcode.y2019.day06

import scala.io.Source

object Day06A {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  case class OrbitalObject(label: String, orbit: List[OrbitalObject] = List()) {
    def checksum(depth: Int = 0): Int = {
      depth + orbit.map(_.checksum(depth + 1)).sum
    }
  }

  def parseOrbital(com: String, input: Seq[(String, String)]): OrbitalObject = {
    val orbit = input.filter(_._1 == com).map(t => parseOrbital(t._2, input)).toList
    OrbitalObject(com, orbit)
  }

  def main(args: Array[String]): Unit = {
    println(parseOrbital("COM", input.getLines().map(s => {
      val parts = s.split("\\)")
      (parts.head, parts.last)
    }).toSeq).checksum())
  }
}
