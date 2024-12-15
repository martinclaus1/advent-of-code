package dev.martinclaus.day11

import dev.martinclaus.Day

class Day11 : Day {
    override val name = "Plutonian Pebbles"

    companion object {
        const val INPUT_FILE = "day11.txt"
    }

    override fun partI(input: String): Long {
        val arrangement = input.split(" ").map { it.trim().toLong() }
        return arrangement.fold(0L) { acc, n -> acc + calculateChanges(n, 0, 25) }
    }

    private val cache = HashMap<Pair<Int, Long>, Long>()
    private fun calculateChanges(stone: Long, blink: Int, maxBlinks: Int): Long {
        if (blink == maxBlinks) return 1
        cache[blink to stone]?.let { return it }

        cache[blink to stone] = if (stone==0L) {
            calculateChanges(1, blink + 1, maxBlinks)
        } else if (stone.toString().length % 2 == 0) {
            val asString = stone.toString()
            val half = asString.length / 2
             calculateChanges(asString.take(half).toLong(), blink + 1, maxBlinks) +
                    calculateChanges(asString.takeLast(half).toLong(), blink + 1, maxBlinks)
        } else {
            calculateChanges(stone*2024, blink + 1, maxBlinks)
        }

        return cache[blink to stone]!!
    }

    override fun partII(input: String): Long {
        val arrangement = input.split(" ").map { it.trim().toLong() }
        return arrangement.fold(0L) { acc, n -> acc + calculateChanges(n, 0, 75) }
    }
}
