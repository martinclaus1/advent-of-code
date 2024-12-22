package dev.martinclaus.utils

import kotlin.math.abs

class Grid<T>(private val data: MutableMap<Point, T>) : Collection<T> {
    override val size: Int
        get() = data.size

    override fun contains(element: T): Boolean = data.containsValue(element)

    override fun containsAll(elements: Collection<T>) = elements.all { this.contains(it) }

    override fun isEmpty(): Boolean = data.isEmpty()
    override fun iterator(): Iterator<T> = data.values.iterator()

    fun getCardinalNeighborPositions(point: Point) = point.getCardinalNeighbors().filter { it in keys }

    fun islandPerimeter(point: Point): Int = point.getCardinalNeighbors().filter { this[it] != this[point] }.size

    val keys: Set<Point>
        get() = data.keys

    val entries get() = data.entries

    operator fun get(key: Point): T? = data[key]
    operator fun get(value: T): Point? = data.entries.find { it.value == value }?.key

    operator fun set(key: Point, value: T) {
        data[key] = value
    }

    fun clone() = Grid(data.toMutableMap())

    companion object {
        fun <T> of(rows: List<List<T>>): Grid<T> {
            val data = rows.flatMapIndexed { y, row ->
                row.mapIndexed { x, value -> Point(x, y) to value }
            }.toMap()

            return Grid(data.toMutableMap())
        }
    }

    fun isHowManyCorners(p: Point) = (Direction.CARDINALS + Direction.CARDINALS.first()).zipWithNext()
        .filter { (d1, d2) ->
            val a = get(p + d1)
            val b = get(p + d2)
            val c = get(p)
            (a != c && b != c) || (a == c && b == c && get(p + d1 + d2) != c)
        }.size

    override fun toString(): String {
        return data.entries.groupBy { it.key.y }
            .map { (_, row) -> row.sortedBy { it.key.x }.joinToString("") { it.value.toString() } }
            .joinToString("\n")
    }
}


data class Point(val x: Int, val y: Int) {
    private val north by lazy { this + Direction.NORTH.toPoint() }
    private val south by lazy { this + Direction.SOUTH.toPoint() }
    private val east by lazy { this + Direction.EAST.toPoint() }
    private val west by lazy { this + Direction.WEST.toPoint() }

    private val northEast by lazy { this + Direction.NORTH_EAST.toPoint() }
    private val northWest by lazy { this + Direction.NORTH_WEST.toPoint() }
    private val southEast by lazy { this + Direction.SOUTH_EAST.toPoint() }
    private val southWest by lazy { this + Direction.SOUTH_WEST.toPoint() }

    operator fun plus(other: Direction): Point = this + other.toPoint()
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)

    fun getCardinalNeighbors(): List<Point> = listOf(north, east, south, west)
    fun getNeighbours(): List<Point> = getCardinalNeighbors() + listOf(northEast, northWest, southEast, southWest)

    fun manhattanDistanceTo(other: Point) = abs(x - other.x) + abs(y - other.y)

    companion object {
        fun of(input: String) = input.split(",").let { Point(it[0].trim().toInt(), it[1].trim().toInt()) }
    }
}

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    NORTH_EAST,
    NORTH_WEST,
    SOUTH_EAST,
    SOUTH_WEST;

    companion object {
        val CARDINALS = listOf(NORTH, EAST, SOUTH, WEST)
    }

    operator fun plus(other: Direction): Direction = when (this) {
        NORTH -> when (other) {
            EAST -> EAST
            WEST -> WEST
            SOUTH -> SOUTH
            else -> NORTH
        }

        EAST -> when (other) {
            EAST -> SOUTH
            NORTH -> EAST
            SOUTH -> WEST
            else -> EAST
        }

        SOUTH -> when (other) {
            EAST -> WEST
            WEST -> EAST
            NORTH -> NORTH
            else -> SOUTH
        }

        WEST -> when (other) {
            EAST -> NORTH
            WEST -> SOUTH
            SOUTH -> EAST
            else -> WEST
        }

        else -> this
    }

    fun toPoint(): Point = when (this) {
        NORTH -> Point(0, -1)
        EAST -> Point(1, 0)
        SOUTH -> Point(0, 1)
        WEST -> Point(-1, 0)
        NORTH_EAST -> Point(1, -1)
        NORTH_WEST -> Point(-1, -1)
        SOUTH_EAST -> Point(1, 1)
        SOUTH_WEST -> Point(-1, 1)
    }
}
