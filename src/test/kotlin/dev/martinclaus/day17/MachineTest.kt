package dev.martinclaus.day17

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class MachineTest {
    @Test
    fun `should set B to 1`() {
        val machine = Machine(0, 0, 9)
        machine.run(listOf(2, 6))

        assertEquals(1, machine.b)
    }

    @Test
    fun `should set B to 26`() {
        val machine = Machine(0, 29, 0)
        machine.run(listOf(1, 7))

        assertEquals(26, machine.b)
    }

    @Test
    fun `should set B to 44354`() {
        val machine = Machine(0, 2024, 43690)
        machine.run(listOf(4, 0))

        assertEquals(44354, machine.b)
    }

    @Test
    fun `should return correct result`() {
        val machine = Machine(10, 0, 0)

        assertEquals(listOf<Long>(0, 1, 2), machine.run(listOf(5, 0, 5, 1, 5, 4)))
    }

    @Test
    fun `should return correct result and leave register A with 0`() {
        val machine = Machine(2024, 0, 0)

        val actual = machine.run(listOf(0, 1, 5, 4, 3, 0))

        assertEquals(listOf<Long>(4, 2, 5, 6, 7, 7, 7, 7, 3, 1, 0), actual)
        assertEquals(machine.a, 0)
    }

}
