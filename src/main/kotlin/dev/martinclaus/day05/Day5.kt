package dev.martinclaus.day05

import dev.martinclaus.Day
import java.util.Collections

class Day5 : Day {
    override val name = "Print Queue"

    companion object {
        const val INPUT_FILE = "day05.txt"
    }

    override fun partI(input: String): Long {
        val (rawRules, rawUpdates) = input.split("(\\h*\\n){2,}".toRegex())
        val (before, after) = getRules(rawRules)
        val updates = getUpdates(rawUpdates)

        return updates.sumOf { update ->
            if (isValidUpdate(before, after, update)) {
                update.elementAt(update.size / 2)
            } else {
                0
            }
        }.toLong()
    }

    override fun partII(input: String): Long {
        val (rawRules, rawUpdates) = input.split("(\\h*\\n){2,}".toRegex())
        val (before, after) = getRules(rawRules)
        val invalidUpdates = getUpdates(rawUpdates).filterNot { isValidUpdate(before, after, it) }

        return invalidUpdates.sumOf { update ->
            val fixedUpdate = update.toMutableList()
            fixUpdate(fixedUpdate, after)
            fixedUpdate.elementAt(fixedUpdate.size / 2)
        }.toLong()
    }

    private fun getRules(rawRules: String): Pair<Map<Int, Set<Int>>, Map<Int, Set<Int>>> {
        val rules = rawRules.lines().map { line ->
            val (a, b) = line.split("|")
            a.toInt() to b.toInt()
        }

        val before = rules.groupBy { it.first }.mapValues { it.value.map { it.second }.toSet() }
        val after = rules.groupBy { it.second }.mapValues { it.value.map { it.first }.toSet() }
        return Pair(before, after)
    }

    private fun getUpdates(rawUpdates: String): List<List<Int>> {
        val updates = rawUpdates.lines().filter { it.isNotBlank() }.map { it.split(",").map { it.toInt() } }
        return updates
    }

    private fun isValidUpdate(before: Map<Int, Set<Int>>, after: Map<Int, Set<Int>>, update: List<Int>): Boolean =
        update.all { i ->
            val index = update.indexOf(i)
            val elementsBefore = update.subList(0, index).toSet()
            val elementsAfter = update.subList(index + 1, update.size).toSet()
            val beforeRule = before[i] ?: emptySet()
            val afterRule = after[i] ?: emptySet()

            afterRule.containsAll(elementsBefore) && beforeRule.containsAll(elementsAfter)
        }

    private fun fixUpdate(update: List<Int>, mustBeAfter: Map<Int, Set<Int>>) {
        var index = 0
        while (index < update.size) {
            val element = update[index]
            val newPredecessor =
                update.subList(index + 1, update.size).lastOrNull { it in (mustBeAfter[element] ?: emptySet()) }
            val predecessorIndex = update.indexOf(newPredecessor)

            if (predecessorIndex != -1) {
                Collections.swap(update, index, predecessorIndex)
                index = 0
            } else {
                index++
            }
        }
    }
}


