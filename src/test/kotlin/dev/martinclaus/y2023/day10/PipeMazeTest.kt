package dev.martinclaus.y2023.day10

import dev.martinclaus.readText
import dev.martinclaus.safeLines
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import java.util.stream.Stream.of

class PipeMazeTest {
    @ParameterizedTest
    @ArgumentsSource(PartIArgumentsProvider::class)
    fun `check part I`(input: List<String>, expected: Int) {
        val actual = partI(input)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun `check part II`(input: List<String>, expected: Int) {
        val actual = partII(input)

        assertEquals(expected, actual)
    }
}

class PartIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
        Arguments.of(
            """
            .....
            .S-7.
            .|.|.
            .L-J.
            .....
            """.trimIndent().lines(),
            4
        ),
        Arguments.of(
            """
            ..F7.
            .FJ|.
            SJ.L7
            |F--J
            LJ...
            """.trimIndent().lines(),
            8
        ),
        Arguments.of(
            "2023/pipe-maze.txt".readText().safeLines(),
            7005
        )
    )
}

class PartIIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
        Arguments.of(
            """
            ...........
            .S-------7.
            .|F-----7|.
            .||.....||.
            .||.....||.
            .|L-7.F-J|.
            .|..|.|..|.
            .L--J.L--J.
            ...........
            """.trimIndent().lines(),
            4
        ),
        Arguments.of(
            """
            .F----7F7F7F7F-7....
            .|F--7||||||||FJ....
            .||.FJ||||||||L7....
            FJL7L7LJLJ||LJ.L-7..
            L--J.L7...LJS7F-7L7.
            ....F-J..F7FJ|L7L7L7
            ....L7.F7||L7|.L7L7|
            .....|FJLJ|FJ|F7|.LJ
            ....FJL-7.||.||||...
            ....L---J.LJ.LJLJ...
            """.trimIndent().lines(),
            8
        ),
        Arguments.of(
            """
            FF7FSF7F7F7F7F7F---7
            L|LJ||||||||||||F--J
            FL-7LJLJ||||||LJL-77
            F--JF--7||LJLJ7F7FJ-
            L---JF-JLJ.||-FJLJJ7
            |F|F-JF---7F7-L7L|7|
            |FFJF7L7F-JF7|JL---7
            7-L-JL7||F7|L7F-7F7|
            L.L7LFJ|||||FJL7||LJ
            L7JLJL-JLJLJL--JLJ.L
            """.trimIndent().lines(),
            10,
        ),
        Arguments.of(
            "2023/pipe-maze.txt".readText().safeLines(),
            417
        )
    )
}
