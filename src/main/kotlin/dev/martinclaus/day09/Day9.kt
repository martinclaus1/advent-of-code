package dev.martinclaus.day09

import dev.martinclaus.Day

class Day9 : Day {
    override val name = "dev.martinclaus.Day 9: Disk Fragmenter"

    companion object {
        const val INPUT_FILE = "day09.txt"
    }

    override fun partI(input: String): Long {
        var fileIndex = 0

        val blocks = input.trim().flatMapIndexed { index, char ->
            val length = char.digitToInt()
            if (index % 2 == 0) {
                (0..<length).map { Block.File(fileIndex) }.also { fileIndex++ }
            } else {
                (0..<length).map { Block.Space }
            }
        }

        var reverseIndex = blocks.size - 1
        var checksum = 0L

        blocks.forEachIndexed { index, block ->
            if (index > reverseIndex) return@forEachIndexed
            when (block) {
                is Block.File -> {
                    checksum += index * block.id
                }
                is Block.Space -> {
                    while (blocks[reverseIndex] is Block.Space) {
                        reverseIndex--
                    }
                    val file = blocks[reverseIndex] as Block.File
                    reverseIndex--
                    checksum += index * file.id
                }
            }
        }

        return checksum
    }

    override fun partII(input: String): Long {
        TODO("Not yet implemented")
    }
}

sealed class Block {
    data class File(val id: Int) : Block()
    data object Space : Block()
}
