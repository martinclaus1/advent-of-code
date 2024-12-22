package dev.martinclaus.y2024.day20

import dev.martinclaus.safeLines
import dev.martinclaus.utils.Grid
import dev.martinclaus.utils.Point

/**
 * --- Day 20: Race Condition ---
 */
class Day20 {
    fun partI(input: String, minimalSavedDistance: Int): Long {
        return solve(input, 2, minimalSavedDistance)
    }

    fun partII(input: String, minimalSavedDistance: Int): Long {
        return solve(input, 20 ,minimalSavedDistance)
    }

    private fun solve(input: String, cheats: Int, minimalSavedDistance: Int): Long {
        val grid = Grid.of(input.safeLines().map { it.toList() })
        var next = grid['S'] ?: return 0
        val end = grid['E'] ?: return 0


        val path = mutableListOf(next)
        val visited = mutableSetOf<Point>()
        while (next != end) {
            visited.add(next)
            next = grid.getCardinalNeighborPositions(next)
                .first { grid[it] in setOf('.', 'E') && it !in visited }
            path.add(next)
        }

        return (0..<path.lastIndex - 1).sumOf { i ->
            (i + 1..<path.size).count { j ->
                val distance = path[i].manhattanDistanceTo(path[j])
                val distanceDoesNotExceedCheats = distance <= cheats
                val savesAtLeastMinimalDistance = j - i - distance >= minimalSavedDistance
                distanceDoesNotExceedCheats && savesAtLeastMinimalDistance
            }
        }.toLong()
    }
}
