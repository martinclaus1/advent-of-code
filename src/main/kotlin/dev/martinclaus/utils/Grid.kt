package dev.martinclaus.utils

class Grid<T>(private val data: Map<Point, T>) : Collection<T> {
    override val size: Int
        get() = data.size

    override fun contains(element: T): Boolean = data.containsValue(element)

    override fun containsAll(elements: Collection<T>) = elements.all { this.contains(it) }

    override fun isEmpty(): Boolean = data.isEmpty()
    override fun iterator(): Iterator<T> = data.values.iterator()

    fun getCardinalNeighborPositions(point: Point) = point.getCardinalNeighbours().filter { it in keys }

    fun islandPerimeter(point: Point): Int = point.getCardinalNeighbours().filter { this[it] != this[point] }.size

    val keys: Set<Point>
        get() = data.keys

    operator fun get(key: Point): T? = data[key]

    companion object {
        fun <T> of(rows: List<List<T>>): Grid<T> {
            val data = rows.flatMapIndexed { y, row ->
                row.mapIndexed { x, value -> Point(x, y) to value }
            }.toMap()

            return Grid(data)
        }
    }

    fun isHowManyCorners(p: Point) = (Direction.entries + Direction.entries.first()).zipWithNext()
        .filter { (d1, d2) ->
            val a = get(p + d1)
            val b = get(p + d2)
            val c = get(p)
            (a != c && b != c) || (a == c && b == c && get(p + d1 + d2) != c)
        }.size
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

    fun getCardinalNeighbours(): List<Point> = listOf(north, east, south, west)
    fun getNeighbours(): List<Point> = getCardinalNeighbours() + listOf(northEast, northWest, southEast, southWest)
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
