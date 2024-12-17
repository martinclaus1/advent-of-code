package dev.martinclaus.day02

import dev.martinclaus.Day

class Day2 : Day<Long> {
    override val name: String = "Red Nosed Reports"

    companion object {
        const val INPUT_FILE = "day02.txt"
    }

    private val pattern = "\\d+".toRegex().toPattern()

    override fun partI(input: String): Long {
        val lines = input.lines().filter { it.isNotBlank() }
            .map { pattern.matcher(it).results().map { i -> i.group().toInt() }.toList() }
        return lines.count {
            isSafe(it)
        }.toLong()
    }
    override fun partII(input: String): Long {
        val lines = input.lines().filter { it.isNotBlank() }
            .map { pattern.matcher(it).results().map { i -> i.group().toInt() }.toList() }
        return lines.count { levels ->
            isSafe(levels) || levels.indices.any { index ->
                val modifiedLevels = levels.filterIndexed { i, _ -> i != index }
                isSafe(modifiedLevels)
            }
        }.toLong()
    }

    private fun isSafe(levels: List<Int>): Boolean {
        val increasing = levels.windowed(2).all { (a, b) -> b in (a + 1)..(a + 3) }
        val decreasing = levels.windowed(2).all { (a, b) -> b in ((a - 1) downTo (a - 3)) }
        return increasing.xor(decreasing)
    }
}


