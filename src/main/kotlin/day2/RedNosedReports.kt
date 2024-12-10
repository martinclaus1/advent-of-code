package dev.martinclaus.day2

import dev.martinclaus.Day

class Day2 : Day {
    override val name: String = "Red Nosed Reports"

    companion object {
        const val INPUT_FILE = "red_nosed_reports.txt"
    }

    private val pattern = "\\d+".toRegex().toPattern()

    override fun partI(input: String): Int {
        val lines = input.lines().filter { it.isNotBlank() }
            .map { pattern.matcher(it).results().map { i -> i.group().toInt() }.toList() }
        return lines.count {
            isSafe(it)
        }
    }
    override fun partII(input: String): Int {
        val lines = input.lines().filter { it.isNotBlank() }
            .map { pattern.matcher(it).results().map { i -> i.group().toInt() }.toList() }
        return lines.count { levels ->
            isSafe(levels) || levels.indices.any { index ->
                val modifiedLevels = levels.filterIndexed { i, _ -> i != index }
                isSafe(modifiedLevels)
            }
        }
    }

    private fun isSafe(levels: List<Int>): Boolean {
        val increasing = levels.windowed(2).all { (a, b) -> b in (a + 1)..(a + 3) }
        val decreasing = levels.windowed(2).all { (a, b) -> b in ((a - 1) downTo (a - 3)) }
        return increasing.xor(decreasing)
    }
}


