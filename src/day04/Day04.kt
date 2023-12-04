package day04

import println
import readInput
import kotlin.math.pow

fun main() {
    val day04 = Day04()
    val input = readInput("day04/day04")
    day04.part1(input).println()
    day04.part2(input).println()
}

class Day04 {
    data class Card(val number: Int, val winningNumbers: Set<Int>, val yourNumbers: Set<Int>)

    private fun List<String>.toCards(): List<Card> {
        return map { line ->
            val subStrings = line.split(':', '|').map { it.trim() }
            Card(
                number = subStrings[0].split(" ").last().toInt(),
                winningNumbers = subStrings[1].split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet(),
                yourNumbers = subStrings[2].split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
            )
        }
    }

    private fun getPoints(matches: Int): Int {
        return 2.0.pow(matches - 1).toInt()
    }

    fun part1(input: List<String>): Int {
        return input.toCards().sumOf { getPoints(it.winningNumbers.intersect(it.yourNumbers).count()) }
    }

    fun part2(input: List<String>) : Int {
        val cards = input.toCards()
        val cardMap = mutableMapOf<Int, Int>()
        return cards.sumOf { card ->
            val points = card.winningNumbers.intersect(card.yourNumbers).count()
            val repetitions = 1 + cardMap.getOrDefault(card.number, 0)
            for (nextCardNumber in card.number..(card.number + points).coerceAtMost(cards.size)) {
                cardMap.merge(nextCardNumber, repetitions) { value, current -> current + value  }
            }
            repetitions
        }
    }
}