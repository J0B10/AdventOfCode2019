package de.ungefroren.adventofcode.y2019.day08

import de.ungefroren.adventofcode.y2019.day07.Day07A.getClass

import scala.annotation.tailrec
import scala.io.Source

object Day08A {

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
    val ordering = Ordering.by[Array[Int], Int](a => a.count(_==0))
    val searchFor = layers.min(ordering)
    val result = searchFor.count(_==1) * searchFor.count(_==2)
    println(result)
  }

}
