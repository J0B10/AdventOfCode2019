package de.ungefroren.adventofcode.y2019.intcodeComputer

/**
 * The computers memory. Automatically grows if values outside the initial program are used
 *
 * @param array the initial program
 */
class Memory(private var array: Array[Long]) {

  def apply(i: Int): Long = {
    if (i >= array.size) {
      0
    } else {
      array(i)
    }
  }

  private[intcodeComputer] def update(i: Int, value: Long): Unit = {
    if (i >= array.size) {
      array = Array.copyOf(array, i + 1)
    }
    array(i) = value
  }

}
object Memory {
  implicit def toSeq(memory: Memory): Seq[Long] = memory.array.toIndexedSeq

  def apply(array: Array[Long]): Memory = new Memory(array)

}