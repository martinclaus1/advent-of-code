package dev.martinclaus.day10

import dev.martinclaus.Day
import dev.martinclaus.utils.Grid
import dev.martinclaus.utils.Point

class Day10 : Day {
    override val name: String = "Hoof It"

    companion object {
        const val INPUT_FILE = "day10.txt"
    }

    override fun partI(input: String): Long {
        val grid = Grid.of(input.lines().dropLastWhile { it.isBlank() }.map { it.map { c -> c.digitToInt() } })
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

    override fun partII(input: String): Long {
        val grid = Grid.of(input.lines().dropLastWhile { it.isBlank() }.map { it.map { c -> c.digitToInt() } })
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
