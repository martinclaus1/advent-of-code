package dev.martinclaus.y2023.day3

import dev.martinclaus.printSolution
import dev.martinclaus.readText
import dev.martinclaus.safeLines

/**
 * @see <a href="https://adventofcode.com/2023/day/3">Advent of Code 2023 Day 3</a>
 */
fun main() {
    val input = "2023/engine-schematic.txt".readText().safeLines()
    printSolution(
        3,
        "Gear Ratios",
        "What is the sum of the part numbers? ${partI(input)}",
        "What is the sum of the gears? ${partII(input)}",
    )
}

private val numberPattern = "\\d+".toRegex().toPattern()
private val charPattern = "[^.0-9]".toRegex().toPattern()
private val asteriskPattern = "\\*".toRegex().toPattern()

fun partI(lines: List<String>): Int {
    val numbers = getNumbers(lines)
    val chars = getCharacters(lines)

    val result = numbers.mapIndexed { index, row ->
        val charsAbove = if (index > 0) chars[index - 1] else emptySet()
        val charsBesides = chars[index]
        val charsBelow = if (index < chars.size - 1) chars[index + 1] else emptySet()

        row.filter { number ->
            val range = number.start - 1..number.end
            isAdjacent(range, charsAbove, charsBesides, charsBelow)
        }.sumOf { it.value }
    }.sum()

    return result
}

fun partII(lines: List<String>): Int {
    val numbers = getNumbers(lines)
    val asterisks = lines.map { line ->
        asteriskPattern.matcher(line).results().map { it.start() }.toList().toSet()
    }

    return asterisks.mapIndexed { index, row ->
        val numbersAbove = if (index > 0) numbers[index - 1] else emptySet()
        val numbersBesides = numbers[index]
        val numbersBelow = if (index < numbers.size - 1) numbers[index + 1] else emptySet()

        getGears(row, numbersAbove, numbersBesides, numbersBelow).sumOf { it.reduce(Int::times) }
    }.sum()
}

/**
 * a gear is an asterisk surrounded by two numbers
 */
private fun getGears(
    row: Set<Int>,
    numbersAbove: Set<Number>,
    numbersBesides: Set<Number>,
    numbersBelow: Set<Number>
) = row.map {
    val range = it - 1..it + 1
    getNumbersAround(numbersAbove, range) + getNumbersAround(numbersBesides, range) + getNumbersAround(
        numbersBelow,
        range
    )
}.filter { it.size == 2 }

private fun isAdjacent(
    range: IntRange,
    charsAbove: Set<Int>,
    charsBesides: Set<Int>,
    charsBelow: Set<Int>
) = hasCharAround(charsAbove, range) || hasCharAround(charsBesides, range) || hasCharAround(
    charsBelow,
    range
)

private fun getCharacters(lines: List<String>) = lines.map { line ->
    charPattern.matcher(line).results().map { it.start() }.toList().toSet()
}

private fun getNumbers(lines: List<String>) = lines.map { line ->
    numberPattern.matcher(line).results().map { Number(it.start(), it.end(), it.group().toInt()) }.toList()
        .toSet()
}

private fun hasCharAround(chars: Set<Int>, range: IntRange): Boolean {
    return chars.any { it in range }
}

private fun getNumbersAround(numbers: Set<Number>, range: IntRange): Set<Int> {
    return numbers.filter { it.start in range || it.end - 1 in range }.map { it.value }.toSet()
}

data class Number(
    val start: Int, // inclusive
    val end: Int, // exclusive
    val value: Int
)
