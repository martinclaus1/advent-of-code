package dev.martinclaus

interface Day {
    val name: String
    fun partI(input: String): Long
    fun partII(input: String): Long
}
