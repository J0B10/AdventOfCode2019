package de.ungefroren.adventofcode.y2019.intcodeComputer


package object instructions {
  implicit val defaults: Seq[Instruction] = Seq(
    Add,
    Multiply,
    InputStdIn,
    OutputStdOut,
    JumpIfTrue,
    JumpIfFalse,
    LessThan,
    Equals,
    Exit
  )
}
