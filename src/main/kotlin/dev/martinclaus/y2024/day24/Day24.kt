package dev.martinclaus.y2024.day24

import dev.martinclaus.safeLines

class Day24 {
    fun partI(input: String): Long {
        val (rawRegisters, rawGates) = input.split("\n\n")
        val registers = rawRegisters.safeLines().map {
            val (register, value) = it.split(":")
            register to value.trim().toInt()
        }.toMap().toMutableMap()

        // ntg XOR fgs -> mjb as pattern
        val gatePattern = Regex("(.+) (.+) (.+) -> (.+)")
        val gates = rawGates.safeLines().map {
            val (a, operation, b, output) = gatePattern.matchEntire(it)!!.destructured
            Gate(a, b, Operation.valueOf(operation.toUpperCase()), output)
        }

        val excluded = mutableSetOf<String>()
        do {
            gates.forEach { it.operate(registers, excluded) }
        } while (excluded.isNotEmpty())

        return registers.filterKeys { it.startsWith("z") }.entries.sortedByDescending { it.key }
            .joinToString("") { it.value.toString() }.toLong(2)
    }

    fun partII(input: String): Long {
        return 0
    }
}

data class Gate(val a: String, val b: String, val operation: Operation, val output: String) {

    fun operate(registers: MutableMap<String, Int>, excluded: MutableSet<String>) {
        val aValue = registers[a]?.also { excluded.remove(a) } ?: run { excluded.add(a); null }
        val bValue = registers[b]?.also { excluded.remove(b) } ?: run { excluded.add(b); null }

        if (aValue == null || bValue == null) {
            return
        }

        val result = when (operation) {
            Operation.AND -> aValue and bValue
            Operation.OR -> aValue or bValue
            Operation.XOR -> aValue xor bValue
        }
        registers[output] = result
    }

}


enum class Operation {
    AND, OR, XOR
}
