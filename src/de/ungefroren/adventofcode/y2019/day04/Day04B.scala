package de.ungefroren.adventofcode.y2019.day04

import scala.io.Source

object Day04B {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  def increasing(tuple: (Int, Int)): Boolean = tuple._1 <= tuple._2

  def main(args: Array[String]): Unit = {
    val range = input.mkString.split("-").map(_.toInt)
    val passwords = range.head to range.last filter (pw => {
      val digits = pw.toString.toCharArray.map(_.asDigit)
      var containsExactDouble = false
      for (i <- digits.indices.tail) {
        if (digits(i) == digits(i-1)) {
          if (i == 1 || digits(i-2) != digits(i)) {
            if (i == digits.indices.last || digits(i+1) != digits(i)) {
              containsExactDouble = true
            }
          }
        }
      }
      containsExactDouble && digits.zip(digits.tail).forall(increasing)
    })
    println(s"${passwords.length} passwords between ${range.head} and ${range.last} meet this criteria")
  }

}
