package dev.martinclaus.day01

import dev.martinclaus.safeLines
import kotlin.math.abs

/**
 * --- Day 1: Historian Hysteria ---
 */
class Day1 {

    fun partI(input: String): Long {
        val (firsts, seconds) = input.pairs()
        return firsts.sorted().zip(seconds.sorted()).sumOf { (a, b) -> abs(b - a) }.toLong()
    }

    fun partII(input: String): Long {
        val (firsts, seconds) = input.pairs()
        val mappedSeconds = seconds.groupBy { it }.mapValues { it.value.size }
        return firsts.sumOf { it * (mappedSeconds[it] ?: 0) }.toLong()
    }

    private fun String.pairs(): Pair<List<Int>, List<Int>> {
        val lists = safeLines().map { line ->
            val (first, second) = line.split("   ").map { match -> match.toInt() }
            first to second
        }
        return lists.map { it.first } to lists.map { it.second }
    }
}
