package dev.martinclaus.day13

import dev.martinclaus.Day

class Day13 : Day {
    override val name = "Claw Contraption"

    companion object {
        const val INPUT_FILE = "day13.txt"
        const val ADDEND = 10000000000000
    }

    private val buttonPattern = Regex("X\\+(\\d+), Y\\+(\\d+)").toPattern()
    private val pricePattern = Regex("X=(\\d+), Y=(\\d+)").toPattern()

    override fun partI(input: String): Long {
        return input.split("\n\n").sumOf { m ->
            val (a, b) = m.toButtons()
            val prize = m.toPrize()
            solve(ClawMachine(a, b, prize))
        }
    }

    override fun partII(input: String): Long {
        return input.split("\n\n").sumOf { m ->
            val (a, b) = m.toButtons()
            val prize = m.toPrize(ADDEND)
            solve(ClawMachine(a, b, prize))
        }
    }

    /**
     * https://de.wikipedia.org/wiki/Cramersche_Regel
     */
    private fun solve(machine: ClawMachine): Long {
        val detA = machine.a.x * machine.b.y - machine.a.y * machine.b.x
        val a = (machine.prize.x * machine.b.y - machine.prize.y * machine.b.x) / detA
        val b = (machine.a.x * machine.prize.y - machine.a.y * machine.prize.x) / detA

        val testX = a * machine.a.x + b * machine.b.x == machine.prize.x
        val textY = a * machine.a.y + b * machine.b.y == machine.prize.y

        return if (testX && textY) a * 3 + b else 0
    }

    private fun String.toButtons(): List<Row> =
        buttonPattern.matcher(this).results().map { Row(it.group(1).toLong(), it.group(2).toLong()) }.toList()

    private fun String.toPrize(addend: Long = 0): Row {
        val prize = pricePattern.matcher(this).results()
            .map { Row(it.group(1).toLong() + addend, it.group(2).toLong() + addend) }.toList().first()
        return prize
    }
}

data class Row(val x: Long, val y: Long)

data class ClawMachine(val a: Row, val b: Row, val prize: Row)
