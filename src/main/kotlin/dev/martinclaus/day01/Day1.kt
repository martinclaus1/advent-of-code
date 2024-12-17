package dev.martinclaus.day01

import dev.martinclaus.Day
import kotlin.math.abs


class Day1: Day<Long> {
    override val name: String = "Historian Hysteria"

    private val pattern = "\\d+".toRegex().toPattern()

    companion object {
        const val INPUT_FILE = "day01.txt"
    }

    override fun partI(input: String): Long {
        val lines = input.lines().filter { it.isNotBlank() }
        val lists = lines.mapNotNull {
            val results = pattern.matcher(it).results().map { it.group() }.toList()
            if (results.size == 2) {
                results[0].toInt() to results[1].toInt()
            } else null
        }

        val first = lists.map { it.first }.sorted()
        val second = lists.map { it.second }.sorted()

        return first.zip(second).sumOf { (a, b) -> abs(b-a) }.toLong()
    }

    override fun partII(input: String): Long {
        val lines = input.lines().filter { it.isNotBlank() }
        val lists = lines.mapNotNull {
            val results = pattern.matcher(it).results().map { it.group() }.toList()
            if (results.size == 2) {
                results[0].toInt() to results[1].toInt()
            } else null
        }

        val first = lists.map { it.first }
        val second = lists.map { it.second }.groupBy { it }.mapValues { it.value.size }

        return first.sumOf { it * (second[it]?:0) }.toLong()
    }
}
