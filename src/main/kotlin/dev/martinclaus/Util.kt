package dev.martinclaus

private val classLoader = object {}.javaClass.classLoader
fun String.readText(): String = classLoader.getResource(this)?.readText() ?: ""
fun String.safeLines() = this.lines().dropLastWhile { it.isBlank() }

@Deprecated("", ReplaceWith(""))
fun printSolution(
    day: Int,
    description: String,
    partI: String,
    partII: String
) = println(buildString { append("Day $day: $description\nPart I: $partI\nPart II: $partII") })
