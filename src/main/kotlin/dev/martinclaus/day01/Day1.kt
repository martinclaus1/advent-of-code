package dev.martinclaus.day01

import dev.martinclaus.Day
import kotlin.math.abs


class Day1: Day<Long> {
    override val name: String = "Historian Hysteria"

    companion object {
        const val INPUT_FILE = "day01.txt"
    }

    override fun partI(input: String): Long {
        val (firsts, seconds) = input.pairs()
        return firsts.sorted().zip(seconds.sorted()).sumOf { (a, b) -> abs(b-a) }.toLong()
    }

    override fun partII(input: String): Long {
        val (firsts, seconds) = input.pairs()
        val mappedSeconds = seconds.groupBy { it }.mapValues { it.value.size }
        return firsts.sumOf { it * (mappedSeconds[it]?:0) }.toLong()
    }

    private fun String.pairs(): Pair<List<Int>, List<Int>> {
        val lines = lines().dropLastWhile { it.isBlank() }
        val lists = lines.map {
            val (first, second) = it.split("   ").map { it.toInt() }
            first to second
        }
        return lists.map { it.first } to lists.map { it.second }
    }
}
