package dev.martinclaus

private val classLoader = object {}.javaClass.classLoader
fun String.readLines(): List<String> = classLoader.getResource(this)?.readText()?.lines()?.filter { it.isNotBlank() } ?: emptyList()
fun String.readText(): String = classLoader.getResource(this)?.readText() ?: ""
