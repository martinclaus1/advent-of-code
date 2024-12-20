package dev.martinclaus.y2023.day1

import dev.martinclaus.readText
import dev.martinclaus.safeLines

/**
 * @see <a href="https://adventofcode.com/2023/day/1">Advent of Code 2023 Day 1</a>
 */
fun main() {
    val input = "2023/trebuchet.txt".readText().safeLines()

    println("Day 1: Trebuchet")
    println("Part II: What is the sum of all of the calibration values? ${partII(input)}")
}

private val numbers = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

fun partII(lines: List<String>): Int {
    return lines.sumOf { input ->
        val firstDigit = getFirstDigit(replaceFromStart(input)).ifEmpty { "0" }
        val lastDigit = getFirstDigit(replaceFromEnd(input).reversed()).ifEmpty { "0" }
        (firstDigit + lastDigit).toInt()
    }
}

private fun getFirstDigit(line: String): String {
    return line.dropWhile { !it.isDigit() }.take(1)
}

private fun replaceFromStart(input: String): String {
    val firstMatch = numbers.entries
        .map { input.indexOf(it.key) to it }
        .filter { (index) -> index >= 0 }
        .minByOrNull { (index) -> index }?.second
    return input.replace(firstMatch?.key.orEmpty(), firstMatch?.value.toString())
}

private fun replaceFromEnd(input: String): String {
    val lastMatch = numbers.entries
        .map { input.lastIndexOf(it.key) to it }
        .maxByOrNull { (index) -> index }?.second
    return input.replace(lastMatch?.key.orEmpty(), lastMatch?.value.toString())
}
