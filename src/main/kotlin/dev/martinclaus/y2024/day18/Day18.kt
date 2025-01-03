package dev.martinclaus.y2024.day18

import dev.martinclaus.safeLines
import dev.martinclaus.utils.Point

/**
 * --- Day 18: RAM Run ---
 */
class Day18 {

    fun partI(input: String, end: Int, offset: Int): Int {
        val points = input.safeLines().map { Point.of(it) }

        return solve(points.take(offset).toSet(), end) ?: 0
    }

    fun partII(input: String, end: Int, offset: Int): String {
        val lines = input.safeLines()
        val points = lines.map { Point.of(it) }

        var max = lines.size
        var min = offset
        while (min < max) {
            val mid = (max + min) / 2
            if (solve(points.take(mid).toSet(), end) == null) {
                max = mid
            } else {
                min = mid + 1
            }
        }
        return points[min - 1].toString()
    }

    private fun solve(points: Set<Point>, exit: Int): Int? {
        val end = Point(exit, exit)
        val queue = ArrayDeque<Pair<Point, Int>>()
        queue.add(Point(0, 0) to 0)
        val visited = HashSet<Point>()

        while (queue.isNotEmpty()) {
            val (current, steps) = queue.removeFirst()

            if (!visited.add(current)) continue
            if (current == end) return steps

            val nextNeighbors = current.getCardinalNeighbors()
                .filter { neighbor ->
                    val xIsInRam = neighbor.x in 0..exit
                    val yIsInRam = neighbor.y in 0..exit
                    val isInPoints = neighbor in points
                    xIsInRam && yIsInRam && !isInPoints
                }
                .map { it to steps + 1 }

            queue.addAll(nextNeighbors)
        }

        return null
    }
}
