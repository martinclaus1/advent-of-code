package dev.martinclaus.y2024.day23

import dev.martinclaus.safeLines

class Day23 {
    fun partI(input: String): Long {
        val connections = input.safeLines().flatMap {
            val (a, b) = it.split("-")
            listOf(a to b, b to a)
        }.groupBy { it.first }.mapValues { it.value.map { it.second }.toSet() }

        val triples = connections.entries.flatMap { (key, connection) ->
            combineElements(connection.toList()).filter { (a, b) ->
                connections[a]?.contains(b) == true && connections[b]?.contains(
                    a
                ) == true
            }.map { (a, b) -> listOf(key, a, b).sorted() }
        }.filter { it.any { it.startsWith("t") } }.toSet()

        return triples.size.toLong()
    }

    fun <T> combineElements(list: List<T>): List<Pair<T, T>> {
        return list.flatMapIndexed { index, element ->
            list.drop(index + 1).map { Pair(element, it) }
        }
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
