package dev.martinclaus.day17

import kotlin.math.pow

class Machine(a: Long, b: Long, c: Long) {
    var a: Long = a
        private set
    var b: Long = b
        private set
    var c: Long = c
        private set

    private val out = mutableListOf<Long>()
    private var pointer = 0

    fun run(instructions: List<Long>): List<Long> {
        pointer = 0
        out.clear()

        while (pointer < instructions.size) {
            operate(instructions[pointer], instructions.getOrNull(pointer + 1) ?: break)
        }
        return out
    }

    private fun adv(literal: Long) {
        a = (a / 2.0.pow(literal.asComboOperand().toDouble())).toLong()
        pointer += 2
    }

    private fun bxl(literal: Long) {
        b = b xor literal
        pointer += 2
    }

    private fun bst(literal: Long) {
        b = literal.asComboOperand() % 8
        pointer += 2
    }

    private fun jnz(literal: Long) {
        if (a == 0L) {
            pointer += 2
        } else {
            pointer = literal.toInt()
        }
    }

    private fun bxc() {
        b = b xor c
        pointer += 2
    }

    private fun out(literal: Long) {
        out.add(literal.asComboOperand() % 8)
        pointer += 2
    }

    private fun bdv(literal: Long) {
        b = (a / 2.0.pow(literal.asComboOperand().toDouble())).toLong()
        pointer += 2
    }

    private fun cdv(literal: Long) {
        c = (a / 2.0.pow(literal.asComboOperand().toDouble())).toLong()
        pointer += 2
    }

    private fun Long.asComboOperand(): Long = when (this) {
        in 0..3 -> this
        4L -> a
        5L -> b
        6L -> c
        7L -> throw IllegalArgumentException("Operand 7 is invalid")
        else -> throw IllegalArgumentException("Unknown operand: $this")
    }

    private fun operate(opcode: Long, operand: Long) {
        when (opcode) {
            0L -> adv(operand)
            1L -> bxl(operand)
            2L -> bst(operand)
            3L -> jnz(operand)
            4L -> bxc()
            5L -> out(operand)
            6L -> bdv(operand)
            7L -> cdv(operand)
        }
    }
}
