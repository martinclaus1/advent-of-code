package dev.martinclaus.y2023.day11

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

class CosmicExpansionTest {

    @ParameterizedTest
    @ArgumentsSource(PartIArgumentsProvider::class)
    fun `check part I`(input: List<String>, expected: Long) {
        val actual = partI(input)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun `check part II`(input: List<String>, factor: Long, expected: Long) {
        val actual = partII(input, factor)

        assertEquals(expected, actual)
    }
}

class PartIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
        Arguments.of(
            """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....
            """.trimIndent().lines(),
            374
        ),
        Arguments.of(
            "2023/cosmic-expansion.txt".readText().safeLines(),
            9639160
        )
    )
}

class PartIIArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
        Arguments.of(
            """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....
            """.trimIndent().lines(),
            100 - 1,
            8410
        ),
        Arguments.of(
            "2023/cosmic-expansion.txt".readText().safeLines(),
            1000_000L - 1,
            752936133304
        )
    )
}
