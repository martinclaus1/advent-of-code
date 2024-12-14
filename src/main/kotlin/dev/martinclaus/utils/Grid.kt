package dev.martinclaus.utils

data class Grid<T>(val data: Map<Point, T>) : Collection<T> {
    override val size: Int
        get() = data.size

    override fun contains(element: T): Boolean = data.containsValue(element)

    override fun containsAll(elements: Collection<T>) = elements.all { this.contains(it) }

    override fun isEmpty(): Boolean = data.isEmpty()
    override fun iterator(): Iterator<T> = data.values.iterator()

    fun getCardinalNeighborPositions(point: Point) = point.getCardinalNeighbours().filter { it in data.keys }

    companion object {
        fun <T> of(rows: List<List<T>>): Grid<T> {
            val data = rows.flatMapIndexed { y, row ->
                row.mapIndexed { x, value -> Point(x, y) to value }
            }.toMap()

            return Grid(data.toMutableMap())
        }
    }
}


data class Point(val x: Int, val y: Int) {
    private val north by lazy { Point(x, y - 1) }
    private val south by lazy { Point(x, y + 1) }
    private val east by lazy { Point(x + 1, y) }
    private val west by lazy { Point(x - 1, y) }

    private val northEast by lazy { Point(x + 1, y - 1) }
    private val northWest by lazy { Point(x - 1, y - 1) }
    private val southEast by lazy { Point(x + 1, y + 1) }
    private val southWest by lazy { Point(x - 1, y + 1) }

    fun getCardinalNeighbours(): List<Point> = listOf(north, east, south, west)
    fun getNeighbours(): List<Point> = getCardinalNeighbours() + listOf(northEast, northWest, southEast, southWest)
}
