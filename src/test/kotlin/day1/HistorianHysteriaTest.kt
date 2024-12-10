package day1

import dev.martinclaus.day1.DAY1_INPUT
import dev.martinclaus.day1.partI
import dev.martinclaus.day1.partII
import dev.martinclaus.readLines
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import java.util.stream.Stream.of
import kotlin.test.assertEquals

class HistorianHysteriaTest {

    @ParameterizedTest
    @ArgumentsSource(PartIArgumentsProvider::class)
    fun checkPartI(input: List<String>, expected: Int) {
        val result = partI(input)
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun checkPartII(input: List<String>, expected: Int) {
        val result = partII(input)
        assertEquals(expected, result)
    }

    class PartIArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
            Arguments.of(
                """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """.trimIndent().lines(),
                11
            ),
            Arguments.of(DAY1_INPUT.readLines(), 2970687)
        )
    }

    class PartIIArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
            Arguments.of(
                """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """.trimIndent().lines(),
                31
            ),
            Arguments.of(DAY1_INPUT.readLines(), 23963899)
        )
    }
}
