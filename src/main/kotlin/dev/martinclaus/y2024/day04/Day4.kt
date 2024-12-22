package dev.martinclaus.y2024.day04

import dev.martinclaus.safeLines

/**
 * --- Day 4: Ceres Search ---
 */
class Day4 {

    // todo: refactor
    fun partI(input: String): Long {
        val grid = input.safeLines()
        val word = "XMAS"
        val directions = listOf(
            Pair(0, 1),   // Right
            Pair(1, 0),   // Down
            Pair(1, 1),   // Down-Right (Diagonal)
            Pair(-1, 0),  // Up
            Pair(0, -1),  // Left
            Pair(-1, -1), // Up-Left (Diagonal)
            Pair(-1, 1),  // Up-Right (Diagonal)
            Pair(1, -1)   // Down-Left (Diagonal)
        )

        val rows = grid.size
        val cols = grid[0].length
        var count = 0

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                directions.forEach { (deltaRow, deltaCol) ->
                    if (word.indices.all { i ->
                            val newRow = row + i * deltaRow
                            val newCol = col + i * deltaCol
                            newRow in 0 until rows && newCol in 0 until cols && grid[newRow][newCol] == word[i]
                        }) {
                        count++
                    }
                }
            }
        }

        return count.toLong()
    }

    // todo: refactor
    fun partII(input: String): Long {
        val grid = input.safeLines()
        val directions = listOf(
            Direction.UP_LEFT,
            Direction.DOWN_RIGHT,
            Direction.UP_RIGHT,
            Direction.DOWN_LEFT
        )

        val rows = grid.size
        val cols = grid[0].length
        var count = 0

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (grid[row][col] == 'A') {
                    val result = directions.mapNotNull { direction ->
                        val newRow = row + direction.y
                        val newCol = col + direction.x
                        try {
                            grid[newRow][newCol]
                        } catch (e: IndexOutOfBoundsException) {
                            null
                        }
                    }

                    if (result.size == 4 && result[0] != result[1] && result[2] != result[3] && result.all { it in "MS" }) {
                        count++
                    }
                }
            }
        }

        return count.toLong()
    }
}

enum class Direction(val y: Int, val x: Int) {
    DOWN_RIGHT(1, 1),
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1)
}
