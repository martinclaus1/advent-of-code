package dev.martinclaus

interface Day<T> {
    val name: String
    fun partI(input: String): T
    fun partII(input: String): T
}
