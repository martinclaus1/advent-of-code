package dev.martinclaus.day06

import dev.martinclaus.safeLines
import dev.martinclaus.utils.Direction
import dev.martinclaus.utils.Point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

/**
 * --- Day 6: Guard Gallivant ---
 */
class Day6 {

    fun partI(input: String): Long {
        val lines = input.safeLines().map { it.toList() }
        return getVisitedCount(lines)?.toLong() ?: 0
    }

    fun partII(input: String): Long = runBlocking {
        val lines = input.safeLines().map { it.toList() }

        val deferredResults = lines.mapIndexed { y, row ->
            List(row.size) { x ->
                CoroutineScope(Dispatchers.IO.limitedParallelism(10)).async {
                    getVisitedCount(lines.modifyCell(Point(x, y), 'O'))
                }
            }
        }.flatten()

        deferredResults.count {
            withTimeoutOrNull(50) { it.await() } == null
        }.toLong()
    }

    private fun getVisitedCount(lines: List<List<Char>>): Int? {

        tailrec fun explore(next: Point, currentDirection: Direction, visited: MutableSet<Point>, obstacleCount: Int): Int? {
            if (obstacleCount == 3) return null
            val candidate = next + currentDirection
            val element = lines.get(candidate) ?: return visited.size

            return if (element != '#' && element != 'O') {
                visited.add(candidate)
                explore(candidate, currentDirection, visited, obstacleCount)
            } else {
                val newDirection = when (currentDirection) {
                    Direction.EAST -> Direction.SOUTH
                    Direction.SOUTH -> Direction.WEST
                    Direction.WEST -> Direction.NORTH
                    Direction.NORTH -> Direction.EAST
                    else -> throw IllegalArgumentException("Invalid direction")
                }
                explore(next, newDirection, visited, if (element == 'O') obstacleCount + 1 else obstacleCount)
            }
        }

        val start = getStart(lines)
        val visited = mutableSetOf(start)
        return explore(start, Direction.NORTH, visited, 0)
    }

    private fun List<List<Char>>.get(point: Point) = try {
        this[point.y][point.x]
    } catch (e: IndexOutOfBoundsException) {
        null
    }

    private fun getStart(lines: List<List<Char>>): Point {
        val start = lines.mapIndexed { y, row ->
            row.mapIndexed { x, c ->
                if (c == '^') Point(x, y) else null
            }.filterNotNull()
        }.flatten().first()
        return start
    }

    private fun List<List<Char>>.modifyCell(point: Point, value: Char): List<List<Char>> = this.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
            if (x == point.x && y == point.y && this[y][x] != '^') value else c
        }
    }
}
