package dev.martinclaus

import org.junit.jupiter.api.DisplayNameGenerator

class CustomDisplayNameGenerator : DisplayNameGenerator.Standard() {
    override fun generateDisplayNameForClass(testClass: Class<*>): String {
        return if (testClass.packageName.contains("day", ignoreCase = true)) {
            val (year, day) = testClass.packageName.split(".").map { it.replaceFirstChar { c -> c.uppercase() } }
                .takeLast(2)
            "${year.drop(1)} - $day - ${testClass.simpleName}"
        } else {
            super.generateDisplayNameForClass(testClass)
        }
    }
}
