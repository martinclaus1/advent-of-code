package dev.martinclaus.day3

import dev.martinclaus.Day

class Day3: Day {
    override val name: String = "Mull It Over"

    companion object {
        const val INPUT_FILE = "mull_it_over.txt"
    }

    override fun partI(input: String): Int {
        val pattern = "mul\\((\\d+),(\\d+)\\)".toRegex().toPattern()

        return pattern.matcher(input).results().map { it.group(1).toInt() * it.group(2).toInt() }.toList().sum()
    }

    override fun partII(input: String): Int {
        val pattern = Regex("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)")
        val group = pattern.findAll(input).map { match ->
            when {
                match.value.startsWith("mul") -> {
                    val (a, b) = match.destructured
                    Match.Mul(match.range.first, a.toInt() * b.toInt())
                }
                match.value == "do()" -> Match.Do(match.range.first)
                match.value == "don't()" -> Match.Dont(match.range.first)
                else -> throw IllegalArgumentException("Unexpected match: ${match.value}")
            }
        }.sortedBy { it.start }

        var apply = true
        return group.sumOf {
            when (it) {
                is Match.Mul -> if (apply) it.value else 0
                is Match.Do -> {
                    apply = true
                    0
                }
                is Match.Dont -> {
                    apply = false
                    0
                }
            }
        }
    }
}

sealed class Match(val start: Int) {
    class Mul(start: Int, val value: Int) : Match(start)
    class Do(start: Int) : Match(start)
    class Dont(start: Int) : Match(start)
}
