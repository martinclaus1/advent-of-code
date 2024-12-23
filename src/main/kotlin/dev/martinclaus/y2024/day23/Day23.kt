package dev.martinclaus.y2024.day23

import dev.martinclaus.safeLines

class Day23 {
    fun partI(input: String): Long {
        val connections = input.safeLines().flatMap {
            val (a, b) = it.split("-")
            listOf(a to b, b to a)
        }.groupBy { it.first }.mapValues { it.value.map { it.second }.toSet() }

        return BronKerbosch2(connections).largestClique().asSequence().filter { it.size >= 3 }
            .flatMap { it.toList().combinations(3) }.map { it.sorted() }.filter { it.any { it.startsWith("t") } }
            .toSet().size.toLong()

    }

    fun partII(input: String): String = BronKerbosch(input.safeLines().flatMap {
        val (a, b) = it.split("-")
        listOf(a to b, b to a)
    }.groupBy { it.first }.mapValues { it.value.map { it.second }.toSet() }).largestClique().sorted().joinToString(",")

}

class BronKerbosch<T>(private val neighbors: Map<T, Set<T>>) {

    private var bestR: Set<T> = emptySet()

    fun largestClique(): Set<T> {
        execute(neighbors.keys)
        return bestR
    }

    private fun execute(
        p: Set<T>,
        r: Set<T> = emptySet(),
        x: Set<T> = emptySet()
    ) {
        if (p.isEmpty() && x.isEmpty()) {
            // We have found a potential best R value, compare it to the best so far.
            if (r.size > bestR.size) bestR = r
        } else {
            val mostNeighborsOfPandX: T = (p + x).maxBy { neighbors.getValue(it).size }!!
            val pWithoutNeighbors = p.minus(neighbors[mostNeighborsOfPandX]!!)
            pWithoutNeighbors.forEach { v ->
                val neighborsOfV = neighbors[v]!!
                execute(
                    p.intersect(neighborsOfV),
                    r + v,
                    x.intersect(neighborsOfV)
                )
            }
        }
    }
}

class BronKerbosch2<T>(private val neighbors: Map<T, Set<T>>) {

    private var bestR = mutableListOf<Set<T>>()

    fun largestClique(): List<Set<T>> {
        execute(neighbors.keys)
        return bestR
    }

    private fun execute(
        p: Set<T>,
        r: Set<T> = emptySet(),
        x: Set<T> = emptySet()
    ) {
        if (p.isEmpty() && x.isEmpty()) {
            // We have found a potential best R value, compare it to the best so far.
            bestR.add(r)
        } else {
            val mostNeighborsOfPandX: T = (p + x).maxBy { neighbors.getValue(it).size }!!
            val pWithoutNeighbors = p.minus(neighbors[mostNeighborsOfPandX]!!)
            pWithoutNeighbors.forEach { v ->
                val neighborsOfV = neighbors[v]!!
                execute(
                    p.intersect(neighborsOfV),
                    r + v,
                    x.intersect(neighborsOfV)
                )
            }
        }
    }
}

fun <T> List<T>.combinations(size: Int): List<List<T>> {
    if (size <= 0) return listOf(emptyList())
    if (size > this.size) return emptyList()
    if (size == this.size) return listOf(this)
    if (size == 1) return this.map { listOf(it) }

    val combinations = mutableListOf<List<T>>()
    val rest = this.drop(1)
    rest.combinations(size - 1).forEach { combination ->
        combinations.add(listOf(this[0]) + combination)
    }
    combinations += rest.combinations(size)
    return combinations
}

