package dev.martinclaus.day6

import dev.martinclaus.Day
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull


class Day6 : Day {
    override val name = "Guard Gallivant"

    companion object {
        const val INPUT_FILE = "guard_gallivant.txt"
    }

    override fun partI(input: String): Int {
        val lines = input.lines().map { it.toList() }
        return getVisitedCount(lines) ?: 0
    }

    private fun getVisitedCount(lines: List<List<Char>>): Int? {

        tailrec fun explore(next: Point, currentDirection: Direction, visited: MutableSet<Point>, obstacleCount: Int): Int? {
            if (obstacleCount == 3) return null
            val candidate = Point(next.x + currentDirection.x, next.y + currentDirection.y)
            val element = lines.get(candidate) ?: return visited.size

            return if (element != '#' && element != 'O') {
                visited.add(candidate)
                explore(candidate, currentDirection, visited, obstacleCount)
            } else {
                val newDirection = when (currentDirection) {
                    Direction.RIGHT -> Direction.DOWN
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.UP
                    Direction.UP -> Direction.RIGHT
                }
                explore(next, newDirection, visited, if(element == 'O') obstacleCount + 1 else obstacleCount)
            }
        }

        val start = getStart(lines)
        val visited = mutableSetOf(start)
        return explore(start, Direction.UP, visited, 0)
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

    override fun partII(input: String): Int = runBlocking {
        val lines = input.lines().map { it.toList() }

        val deferredResults = lines.mapIndexed { y, row ->
            List(row.size) { x ->
                CoroutineScope(Dispatchers.IO.limitedParallelism(10)).async {
                    getVisitedCount(lines.modifyCell(Point(x, y), 'O'))
                }
            }
        }.flatten()

        deferredResults.count {
            withTimeoutOrNull(50) { it.await() } == null
        }
    }
}

data class Point(val x: Int, val y: Int)

enum class Direction(val y: Int, val x: Int) {
    DOWN(1, 0),
    UP(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
}



