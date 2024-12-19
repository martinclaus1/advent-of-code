package dev.martinclaus.day02

import dev.martinclaus.safeLines

/**
 * --- Day 2: Red Nosed Reports ---
 */
class Day2 {
    private val pattern = "\\d+".toRegex().toPattern()

    fun partI(input: String): Long {
        val lines = input.safeLines()
            .map { pattern.matcher(it).results().map { i -> i.group().toInt() }.toList() }
        return lines.count {
            isSafe(it)
        }.toLong()
    }

    fun partII(input: String): Long {
        val lines = input.safeLines()
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


