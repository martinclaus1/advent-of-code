package dev.martinclaus.day12

import dev.martinclaus.Day
import dev.martinclaus.safeLines
import dev.martinclaus.utils.Grid
import dev.martinclaus.utils.Point

class Day12: Day<Long> {
    override val name = "Garden Groups"

    companion object {
        const val INPUT_FILE = "day12.txt"
    }

    override fun partI(input: String): Long {
        return solve(input, Grid<Char>::islandPerimeter)
    }

    override fun partII(input: String): Long {
        return solve(input, Grid<Char>::isHowManyCorners)
    }

    private fun solve(input: String, calculator: (Grid<Char>, Point) -> Int): Long {
        val grid = Grid.of(input.safeLines().map { it.toList() })
        val visited = HashSet<Point>()

        return grid.keys.sumOf { point ->
            val connected = HashSet<Point>()
            val queue = ArrayDeque<Point>()
            queue.add(point)
            var sum = 0
            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                if (!visited.add(current)) continue
                connected.add(current)
                sum += calculator(grid, current)
                queue.addAll(grid.getCardinalNeighborPositions(current).filter { grid[it] == grid[current] })
            }
            sum * connected.size
        }.toLong()
    }
}
