package dev.martinclaus.y2024.day08

import dev.martinclaus.safeLines

/**
 * --- Day 8: Resonant Collinearity ---
 */
class Day8 {

    fun partI(input: String): Long {
        val lines = input.safeLines()
        val antennas = lines.asAntennas()

        val result = antennas.flatMap { (_, points) ->
            points.flatMap { point ->
                val otherPoints = points.filterNot { it == point }
                val candidates = otherPoints.map { otherPoint -> getNext(point, otherPoint) }
                candidates.filter { lines.hasPoint(it) }
            }
        }
        return result.toSet().size.toLong()
    }

    fun partII(input: String): Long {
        val lines = input.safeLines()

        val antennas = lines.asAntennas()

        return antennas.flatMap { (_, points) ->
            points.flatMap { point ->
                val otherPoints = points.filterNot { it == point }
                otherPoints.flatMap { otherPoint ->
                    walk(point, otherPoint, lines)
                }
            }
        }.toSet().size.toLong()
    }

    private fun List<String>.asAntennas(): Map<Char, List<Point>> = flatMapIndexed { y, line ->
        line.mapIndexed { index, c -> c to Point(index, y) }
    }
        .groupBy { (key, _) -> key }
        .mapValues { (_, value) -> value.map { it.second } }
        .filterKeys { it.isLetterOrDigit() }

    private fun walk(a: Point, b: Point, grid: List<String>): List<Point> {
        var x = a
        var y = b
        var next = getNext(x, y)
        val result = mutableListOf(b)
        while (grid.hasPoint(next)) {
            result.add(next)
            x = y
            y = next
            next = getNext(x, y)
        }
        return result
    }

    private fun getNext(a: Point, b: Point): Point {
        val deltaX = a.x - b.x
        val deltaY = a.y - b.y
        return Point(b.x - deltaX, b.y - deltaY)
    }

    private fun List<String>.hasPoint(point: Point): Boolean = try {
        this[point.y][point.x]
        true
    } catch (e: IndexOutOfBoundsException) {
        false
    }
}

data class Point(val x: Int, val y: Int)
