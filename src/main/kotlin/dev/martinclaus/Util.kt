package dev.martinclaus

private val classLoader = object {}.javaClass.classLoader
fun String.readText(): String = classLoader.getResource(this)?.readText() ?: ""
fun String.safeLines() = this.lines().dropLastWhile { it.isBlank() }
