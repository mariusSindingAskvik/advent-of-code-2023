package day01

import println
import readInput

fun main() {
    val words = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    fun String.findFirstDigit(allowWords: Boolean): Int {
        return if (allowWords) {
            var char = '0'
            run breaking@{
                this.forEachIndexed { index, c ->
                    val sliced = try {
                        slice(index..index + 4)
                    } catch (e: StringIndexOutOfBoundsException) {
                        slice(index..lastIndex)
                    }
                    when {
                        c.isDigit() -> char = c
                        else -> words.forEach { (word, number) ->
                            if (sliced.contains(word)) {
                                val replaced = sliced.replace(word, number.toString())
                                replaced.first { it.isDigit() }.let { char = it }
                                return@breaking
                            }
                        }
                    }
                    if (char != '0') return@breaking
                }
            }
            char
        } else {
            this.first { it.isDigit() }
        }.digitToInt()
    }

    fun String.findLastDigit(allowWords: Boolean): Int {
        return if (allowWords) {
            var char = '0'
            run breaking@ {
                for (index in this.indices.reversed()) {
                    val c = this[index]
                    val sliced = try {
                        slice(index-4..index)
                    } catch (e: StringIndexOutOfBoundsException) {
                        slice(0..index)
                    }
                    when {
                        c.isDigit() -> char = c
                        else -> words.forEach { (word, number) ->
                            if (sliced.contains(word)) {
                                val replaced = sliced.replace(word, number.toString())
                                replaced.last { it.isDigit() }.let { char = it }
                                return@breaking
                            }
                        }
                    }
                    if (char != '0') return@breaking
                }
            }
            char
        } else {
            this.last { it.isDigit() }
        }.digitToInt()
    }

    fun String.calibrationValue(allowWords: Boolean): Int {
        return "${this.findFirstDigit(allowWords)}${this.findLastDigit(allowWords)}".toInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { it.calibrationValue(false) }
    }

    fun part2(input: List<String>): Int {
        input.map { it to it.calibrationValue(true) }.forEach {
            println(it)
        }
        return input.sumOf { it.calibrationValue(true) }
    }

    val input = readInput("day01/day01")
    part1(input).println()
    part2(input).println()
}