package dev.martinclaus.y2024.day22

import dev.martinclaus.safeLines

private const val elements = 2000

class Day22 {
    fun partI(input: String): Long {
        val buyers = input.safeLines().map { it.toLong() }

        return buyers.sumOf { it.calc().drop(elements).first() }
    }

    fun partII(input: String): Long {
        val buyers = input.safeLines().map { it.toLong() }
        val prices = mutableMapOf<List<Int>, Int>().withDefault { 0 }

        buyers.forEach { buyer ->
            val secrets = buyer.calc().take(elements).map { it.toInt() % 10 }.toList()
            val changes = secrets.zipWithNext { a, b -> b - a }
            val visited = HashSet<List<Int>>()

            changes.windowed(4).forEachIndexed { i, p ->
                if (visited.add(p)) prices[p] = prices.getValue(p) + secrets[i + 4]
            }
        }

        return prices.maxOf { it.value }.toLong()
    }

    private fun Long.calc() = generateSequence(this) {
        var secret = it.mix(it * 64).prune()
        secret = secret.mix(secret / 32).prune()
        secret.mix(secret * 2048).prune()
    }

    private fun Long.mix(secret: Long): Long {
        var s1 = xor(secret)
        s1 %= 16777216
        return s1
    }

    private fun Long.prune(): Long {
        var s1 = this
        s1 %= 16777216
        return s1
    }
}
