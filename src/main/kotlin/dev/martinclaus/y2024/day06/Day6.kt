package dev.martinclaus.y2024.day06

import dev.martinclaus.safeLines
import dev.martinclaus.utils.Direction
import dev.martinclaus.utils.Point

private const val freeCell = '.'
private const val blockedCell = '#'
private const val start = '^'

/**
 * --- Day 6: Guard Gallivant ---
 */
class Day6 {

    fun partI(input: String): Long {
        val lines = input.safeLines().map { it.toList() }

        return getPath(lines).size.toLong()
    }

    fun partII(input: String): Long {
        val lines = input.safeLines().map { it.toList() }
        val visited = mutableSetOf<Pair<Point, Direction>>()
        val start = lines.getStart()
        val existingPath = getPath(lines)

        return existingPath.filter { lines.get(it) == freeCell }.count { obstacle ->
            visited.clear()
            var point = start
            var direction = Direction.NORTH
            while (true) {
                // path already visited
                if (!visited.add(point to direction)) {
                    return@count true
                }

                // left grid -> no loop
                if (lines.get(point) == null) {
                    return@count false
                }

                // move to next point
                if (lines.get(point + direction) == blockedCell || point + direction == obstacle) {
                    direction += Direction.EAST
                } else {
                    point += direction
                }
            }

            false
        }.toLong()
    }

    private fun getPath(lines: List<List<Char>>): Set<Point> {
        val visited = mutableSetOf<Pair<Point, Direction>>()
        var point = lines.getStart()
        var direction = Direction.NORTH

        while (point to direction !in visited && lines.get(point) != null) {
            visited.add(point to direction)
            if (lines.get(point + direction) == blockedCell) {
                direction += Direction.EAST
            }
            point += direction
        }

        return visited.map { it.first }.toSet()
    }

    private fun List<List<Char>>.get(point: Point) = try {
        this[point.y][point.x]
    } catch (e: IndexOutOfBoundsException) {
        null
    }

    private fun List<List<Char>>.getStart(): Point {
        val start = mapIndexed { y, row ->
            row.mapIndexed { x, c ->
                if (c == start) Point(x, y) else null
            }.filterNotNull()
        }.flatten().first()
        return start
    }
}
