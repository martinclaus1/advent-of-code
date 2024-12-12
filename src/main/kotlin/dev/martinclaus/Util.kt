package dev.martinclaus.dev.martinclaus

private val classLoader = object {}.javaClass.classLoader
fun String.readText(): String = classLoader.getResource(this)?.readText() ?: ""
