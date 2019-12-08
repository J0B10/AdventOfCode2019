package de.ungefroren.adventofcode.y2019.intcodeComputer.instructions

import de.ungefroren.adventofcode.y2019.intcodeComputer.Instruction

/**
 * Read in a value and write it to the memory at the given position
 */
abstract class Input extends Instruction(3, 1) {

}
