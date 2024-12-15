package dev.martinclaus.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class GridTest {
    @Test
    fun `should calculate the island perimeter`() {
        val input = """
        AAAA
        BBCD
        BBCC
        EEEC
        """.trimIndent()

        val grid = Grid.of(input.lines().map { it.toList() })

        assertEquals(3, grid.islandPerimeter(Point(0, 0)))
        assertEquals(2, grid.islandPerimeter(Point(0, 1)))
        assertEquals(2, grid.islandPerimeter(Point(2, 2)))
    }
}
