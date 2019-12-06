package de.ungefroren.adventofcode.y2019.day06

import scala.io.Source

object Day06B {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  case class OrbitalObject(label: String, orbit: List[OrbitalObject] = List()) {
    def checksum(depth: Int = 0): Int = {
      depth + orbit.map(_.checksum(depth + 1)).sum
    }

    def findPathTowards(label: String) : List[String] = {
      if (orbit.exists(_.label.equals(label))) {
        List(label, this.label)
      } else {
        orbit.iterator.map(_.findPathTowards(label)).filter(_.nonEmpty).toList.map(_ :+ this.label).headOption.getOrElse(List())
      }
    }
  }

  def parseOrbital(com: String, input: Seq[(String, String)]): OrbitalObject = {
    val orbit = input.filter(_._1 == com).map(t => parseOrbital(t._2, input)).toList
    OrbitalObject(com, orbit)
  }

  def main(args: Array[String]): Unit = {
    val orbits = parseOrbital("COM", input.getLines().map(s => {
      val parts = s.split("\\)")
      (parts.head, parts.last)
    }).toSeq)
    val you_path = orbits.findPathTowards("YOU")
    val san_path = orbits.findPathTowards("SAN")
    val transfers = you_path.zipWithIndex.collectFirst({
      case (orbit, i) if san_path.contains(orbit) =>
        san_path.zipWithIndex.collectFirst({
          case (orbit_san, j) if orbit_san == orbit =>
            i+j-2
        }).get
    }).get
    println(s"A minimum of $transfers transfers is required")
  }
}
