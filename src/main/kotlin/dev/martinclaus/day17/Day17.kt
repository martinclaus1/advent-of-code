package dev.martinclaus.day17

import dev.martinclaus.Day

class Day17 : Day<String> {
    override val name = "Chronospatial Computer"

    companion object {
        const val INPUT_FILE = "day17.txt"
    }

    private val pattern = "\\d+".toRegex().toPattern()

    override fun partI(input: String): String {
        val (register, program) = getParsedPuzzle(input)

        return Machine(register.first, register.second, register.third).run(program).joinToString(",")
    }

    override fun partII(input: String): String {
        val (_, program) = getParsedPuzzle(input)

        return findA(program, program).toString()
    }

    private fun getParsedPuzzle(input: String): Pair<Triple<Long, Long, Long>, List<Long>> {
        val (registers, rawProgram) = input.split("\n\n")
        val (a, b, c) = pattern.matcher(registers).results().map { it.group().toLong() }.toList()
        val program = pattern.matcher(rawProgram).results().map { it.group().toLong() }.toList()

        return Triple(a, b, c) to program
    }

    private fun findA(program: List<Long>, target: List<Long>): Long {
        var start = if (target.size == 1) {
            0L
        } else {
            8L * findA(program, target.subList(1, target.size))
        }

        while (Machine(start, 0, 0).run(program) != target) {
            start++
        }

        return start
    }
}
