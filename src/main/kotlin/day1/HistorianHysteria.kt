package dev.martinclaus.day1

import dev.martinclaus.readLines
import kotlin.math.abs

const val DAY1_INPUT = "historian_hysteria.txt"
fun main() {
    val input = DAY1_INPUT.readLines()
    println("Day 1: Historian Hysteria")
    println(partI(input))
    println(partII(input))
}


private val pattern = "\\d+".toRegex().toPattern()

fun partI(lines: List<String>): Int {
    val lists = lines.mapNotNull {
        val results = pattern.matcher(it).results().map { it.group() }.toList()
        if (results.size == 2) {
            results[0].toInt() to results[1].toInt()
        } else null
    }

    val first = lists.map { it.first }.sorted()
    val second = lists.map { it.second }.sorted()

    return first.zip(second).sumOf { (a, b) -> abs(b-a) }
}

fun partII(lines: List<String>): Int {
    val lists = lines.mapNotNull {
        val results = pattern.matcher(it).results().map { it.group() }.toList()
        if (results.size == 2) {
            results[0].toInt() to results[1].toInt()
        } else null
    }

    val first = lists.map { it.first }
    val second = lists.map { it.second }.groupBy { it }.mapValues { it.value.size }

    return first.sumOf { it * (second[it]?:0) }
}
