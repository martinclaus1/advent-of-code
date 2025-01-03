package dev.martinclaus.y2024.day17

import dev.martinclaus.readText
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import java.util.stream.Stream.of
import kotlin.test.assertEquals

private const val input = "2024/day17.txt"

class Day17Test {
    private val sut = Day17()

    @ParameterizedTest
    @ArgumentsSource(PartIArgumentsProvider::class)
    fun checkPartI(input: String, expected: String) {
        val result = sut.partI(input)
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun checkPartII(input: String, expected: String) {
        val result = sut.partII(input)
        assertEquals(expected, result)
    }

    class PartIArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
            Arguments.of(
                """
            Register A: 729
            Register B: 0
            Register C: 0
            
            Program: 0,1,5,4,3,0
            """.trimIndent(), "4,6,3,5,6,3,5,2,1,0"
            ),
            Arguments.of(input.readText(), "6,5,7,4,5,7,3,1,0")
        )
    }

    class PartIIArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
            Arguments.of(
                """
            Register A: 2024
            Register B: 0
            Register C: 0
            
            Program: 0,3,5,4,3,0
            """.trimIndent(), "117440"
            ),
            Arguments.of(input.readText(), "105875099912602")
        )
    }
}
