package dev.martinclaus.day09

import kotlin.math.min

/**
 * --- Day 9: Disk Fragmenter ---
 */
class Day9 {

    fun partI(input: String): Long {
        val blocks = readBlocks(input)

        var reverseIndex = blocks.size - 1
        var checksum = 0L
        var offset = 0L
        for (index in blocks.indices) {
            when (val block = blocks[index]) {
                is Block.File -> {
                    val longRange = offset..<(offset + block.length)
                    val sumOf = longRange.sumOf { it * block.id }
                    checksum += sumOf
                    offset += block.length
                }

                is Block.Space -> {
                    val files = mutableListOf<Block.File>()
                    for (i in reverseIndex downTo index) {
                        if (blocks[i] is Block.File) {
                            reverseIndex = i
                            files.add(blocks[i] as Block.File)
                        }
                        if (files.sumOf { it.length } >= block.length) {
                            break
                        }
                    }

                    files.forEach { file ->
                        val min = min(block.length, file.length)
                        val longRange = offset..<(offset + min)
                        val sumOf = longRange.sumOf { it * file.id }
                        checksum += sumOf
                        offset += min
                        block.length -= min
                        file.length -= min
                    }
                }
            }
        }

        return checksum
    }

    fun partII(input: String): Long {
        val blocks = readBlocks(input)

        var checksum = 0L
        var offset = 0L

        for (index in blocks.indices) {
            when (val block = blocks[index]) {
                is Block.File -> {
                    if (block.id >= 0) {
                        val longRange = offset..<(offset + block.length)
                        val sumOf = longRange.sumOf { it * block.id }
                        checksum += sumOf
                    }
                    offset += block.length
                }

                is Block.Space -> {
                    val files = mutableListOf<Block.File>()
                    val reverseIndex = blocks.size - 1
                    for (i in reverseIndex downTo index) {
                        val file = blocks[i] as? Block.File ?: continue
                        if (file.id >= 0 && file.length + files.sumOf { it.length } <= block.length) {
                            files.add(file)
                        }
                    }

                    files.forEach { file ->
                        val min = min(block.length, file.length)
                        val longRange = offset..<(offset + min)
                        val sumOf = longRange.sumOf { it * file.id }
                        checksum += sumOf
                        offset += min
                        file.id = -1
                    }

                    offset += block.length - files.sumOf { it.length }
                }
            }
        }

        return checksum
    }

    private fun readBlocks(input: String): List<Block> {
        var fileIndex = 0

        val blocks = input.trim().mapIndexed { index, char ->
            val length = char.digitToInt()
            if (index % 2 == 0) {
                Block.File(fileIndex, length).also { fileIndex++ }
            } else {
                Block.Space(length)
            }
        }
        return blocks
    }
}

sealed class Block {
    data class File(var id: Int, var length: Int) : Block()
    data class Space(var length: Int) : Block()
}
