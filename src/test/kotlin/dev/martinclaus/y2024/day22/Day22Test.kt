package dev.martinclaus.y2024.day22

import dev.martinclaus.readText
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import java.util.stream.Stream.of
import kotlin.test.assertEquals

private const val input = "2024/day22.txt"

class Day22Test {
    private val sut = Day22()

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
            Arguments.of(
                """
            1
            10
            100
            2024
            """.trimIndent(), 37327623
            ),
            Arguments.of(input.readText(), 15303617151)
        )
    }

    class PartIIArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
            Arguments.of(
                """
            1
            2
            3
            2024
            """.trimIndent(), 23
            ),
            Arguments.of(
                """
            1
            2
            3
            2024
            """.trimIndent(), 23
            ),
            Arguments.of(input.readText(), 1727)
        )
    }
}
