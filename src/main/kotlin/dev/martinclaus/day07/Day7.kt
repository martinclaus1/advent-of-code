package dev.martinclaus.day07

import dev.martinclaus.Day
import dev.martinclaus.safeLines

class Day7: Day<Long> {
    override val name = "Bridge Repair"

    companion object {
        const val INPUT_FILE = "day07.txt"
    }

    override fun partI(input: String): Long {
        val lines = input.safeLines()
        val rows = lines.map { line ->
            val (first, second) = line.split(":")
            val elements = second.trim().split(" ").map { it.toLong() }
            Row(first.toLong(), elements)
        }

        val result = rows.filter {
            val permutations = generatePermutations(it.elements.size - 1, listOf('+', '*'))
            val results = permutations.map { permutation ->
                val ops = permutation.toList()
                it.elements.reduceIndexed { index, acc, i -> calc(acc, ops[0.coerceAtLeast(index - 1)], i) }
            }
            results.any { r -> r == it.result }
        }.sumOf { it.result }

        return result
    }

    override fun partII(input: String): Long {
        val lines = input.safeLines()
        val rows = lines.map { line ->
            val (first, second) = line.split(":")
            val elements = second.trim().split(" ").map { it.toLong() }
            Row(first.toLong(), elements)
        }

        val result = rows.filter {
            val permutations = generatePermutations(it.elements.size - 1, listOf('+', '*', '|'))
            val results = permutations.map { permutation ->
                val ops = permutation.toList()
                it.elements.reduceIndexed { index, acc, i -> calc(acc, ops[0.coerceAtLeast(index - 1)], i) }
            }
            results.any { r -> r == it.result }
        }.sumOf { it.result }

        return result
    }

    private fun generatePermutations(n: Int, prefixes: List<Char>): MutableList<String> {
        val result = mutableListOf<String>()

        fun generate(prefix: String) {
            if (prefix.length == n) {
                result.add(prefix)
                return
            }
            for (char in prefixes) {
                generate(prefix + char)
            }
        }

        generate("")

        return result
    }

    private fun calc(left: Long, op: Char, right: Long) = when (op) {
        '+' -> left + right
        '*' -> left * right
        '|' -> "$left$right".toLong()
        else -> error("Unknown operator")
    }
}

data class Row(val result: Long, val elements: List<Long>)




