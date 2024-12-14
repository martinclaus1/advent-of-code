package dev.martinclaus.day10

import dev.martinclaus.dev.martinclaus.readText
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import java.util.stream.Stream.of
import kotlin.test.assertEquals

class Day10Test {
    private val sut = Day10()

    @ParameterizedTest
    @ArgumentsSource(PartIArgumentsProvider::class)
    fun checkPartI(input: String, expected: Long) {
        val result = sut.partI(input)
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun checkPartII(input: String, expected: Long) {
        val result = sut.partII(input)
        assertEquals(expected, result)
    }

    class PartIArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
            Arguments.of("""
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732
            """.trimIndent(), 36),
            Arguments.of(Day10.INPUT_FILE.readText(), 468)
        )
    }

    class PartIIArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
            Arguments.of("""
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732
            """.trimIndent(), 81),
            Arguments.of(Day10.INPUT_FILE.readText(), 966)
        )
    }
}
