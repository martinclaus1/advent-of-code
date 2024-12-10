package day6

import dev.martinclaus.day5.Day5
import dev.martinclaus.day6.Day6
import dev.martinclaus.readText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream
import java.util.stream.Stream.of
import kotlin.coroutines.CoroutineContext
import kotlin.test.assertEquals

class GuardGallivantTest {
    private val sut = Day6()

    @ParameterizedTest
    @ArgumentsSource(PartIArgumentsProvider::class)
    fun checkPartI(input: String, expected: Int) {
        val result = sut.partI(input)
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @ArgumentsSource(PartIIArgumentsProvider::class)
    fun checkPartII(input: String, expected: Int) = runTest {
        val result =  sut.partII(input)
        assertEquals(expected, result)
    }

    class PartIArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
            Arguments.of(
                """
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...
                """.trimIndent(),
                41
            ),
            Arguments.of(Day6.INPUT_FILE.readText(), 4374)
        )
    }

    class PartIIArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>? = of(
            Arguments.of(
                """
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...
                """.trimIndent(),
                6
            ),
            Arguments.of(Day6.INPUT_FILE.readText(), 1705)
        )
    }
}
