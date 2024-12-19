package dev.martinclaus.day10

import dev.martinclaus.safeLines
import dev.martinclaus.utils.Grid
import dev.martinclaus.utils.Point

/**
 * --- Day 10: Hoof It ---
 */
class Day10 {

    fun partI(input: String): Long {
        val grid = Grid.of(input.safeLines().map { it.map { c -> c.digitToInt() } })
        val starts = grid.keys.filter { grid[it] == 0 }

        return starts.sumOf { point ->
            val visited = HashSet<Point>()
            val queue = ArrayDeque<Point>()
            queue.add(point)

            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                if (!visited.add(current)) continue
                val elements =
                    grid.getCardinalNeighborPositions(current).filter { grid[it] == grid[current]!! + 1 }
                queue.addAll(elements)
            }
            visited.count { grid[it] == 9 }
        }.toLong()
    }

    fun partII(input: String): Long {
        val grid = Grid.of(input.safeLines().map { it.map { c -> c.digitToInt() } })
        val starts = grid.keys.filter { grid[it] == 0 }

        return starts.sumOf { point ->
            val visited = HashSet<List<Point>>()
            val queue = ArrayDeque<List<Point>>()
            queue.add(listOf(point))

            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                if (!visited.add(current)) continue
                val last = current.last()
                val elements = grid.getCardinalNeighborPositions(last).filter { grid[it] == grid[last]!! + 1 }
                    .map { current + it }
                queue.addAll(elements)
            }
            visited.count { grid[it.last()] == 9 }
        }.toLong()
    }
}
