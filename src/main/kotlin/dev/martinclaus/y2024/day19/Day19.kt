package dev.martinclaus.y2024.day19

/**
 * --- Day 19: Linen Layout ---
 */
class Day19 {

    private val cache = mutableMapOf<String, Long>()

    fun partI(input: String): Long {
        val (towelsInput, designsInput) = input.split("\n\n")
        val towels = towelsInput.split(",").map { it.trim() }
        val designs = designsInput.split("\n").dropLastWhile { it.isBlank() }.map { it.trim() }

        return designs.count { solve(it, towels) >= 1 }.toLong()
    }

    fun partII(input: String): Long {
        val (towelsInput, designsInput) = input.split("\n\n")
        val towels = towelsInput.split(",").map { it.trim() }
        val designs = designsInput.split("\n").dropLastWhile { it.isBlank() }.map { it.trim() }


        return designs.sumOf { solve(it, towels) }
    }

    private fun solve(design: String, towels: List<String>): Long {
        if (design.isEmpty()) {
            return 1L
        }
        cache[design]?.let { return it }
        return towels.sumOf { towel ->
            if (design.startsWith(towel)) {
                solve(design.substring(towel.length), towels)
            } else {
                0
            }
        }.also { cache[design] = it }
    }
}
