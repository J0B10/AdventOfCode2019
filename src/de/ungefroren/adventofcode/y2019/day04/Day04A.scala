package de.ungefroren.adventofcode.y2019.day04

import scala.io.Source

object Day04A {

  val input = Source.fromInputStream(getClass.getResourceAsStream("puzzle_input.txt"))

  def identical(tuple: (Int, Int)): Boolean = tuple._1 == tuple._2

  def increasing(tuple: (Int, Int)): Boolean = tuple._1 <= tuple._2

  def main(args: Array[String]): Unit = {
    val range = input.mkString.split("-").map(_.toInt)
    val passwords = range.head to range.last filter (pw => {
      val digits = pw.toString.toCharArray.map(_.asDigit)
      digits.zip(digits.tail).exists(identical) && digits.zip(digits.tail).forall(increasing)
    })
    println(s"${passwords.length} passwords between ${range.head} and ${range.last} meet this criteria")
  }

}
