package day02

import println
import readInput

fun main() {
    val day02 = Day02()
    val input = readInput("day02/day02")
    day02.part1(input).println()
    day02.part2(input).println()
}

class Day02 {
    data class Game(
        val number: Int,
        val bagContents: List<BagContent>,
    )

    data class BagContent(
        val redCubes: Int,
        val greenCubes: Int,
        val blueCubes: Int,
    )

    private fun BagContent.isPossible(): Boolean {
        return when {
            redCubes > 12 -> false
            greenCubes > 13 -> false
            blueCubes > 14 -> false
            else -> true
        }
    }

    private fun Game.getIdOrZero(): Int {
        bagContents.forEach {
            if (!it.isPossible()) return 0
        }
        return number
    }

    private fun Game.filterMin(): Int {
        val minRedCube = bagContents.filter { it.redCubes > 0 }.maxOf { it.redCubes }
        val minBlueCube = bagContents.filter { it.blueCubes > 0 }.maxOf { it.blueCubes }
        val minGreenCube = bagContents.filter { it.greenCubes > 0 }.maxOf { it.greenCubes }
        return minRedCube * minBlueCube * minGreenCube
    }

    private fun List<String>.toBagContents(): List<BagContent> {
        return this.map { list ->
            var bagContent = BagContent(0, 0, 0)
            val cubes = list.split(',').map { it.trim() }
            cubes.forEach { cube ->
                val cubeContent = cube.split(' ')
                when {
                    cube.contains("red") -> bagContent = bagContent.copy(redCubes = cubeContent.first().toInt())
                    cube.contains("green") -> bagContent = bagContent.copy(greenCubes = cubeContent.first().toInt())
                    cube.contains("blue") -> bagContent = bagContent.copy(blueCubes = cubeContent.first().toInt())
                }
            }
            bagContent
        }
    }

    private fun String.toGame(): Game {
        val split = this.split(':', ';')
        val gameNumber = split.first().substringAfterLast(' ').toInt()
        val bagContents = split.subList(1, split.size).toBagContents()
        return Game(number = gameNumber, bagContents = bagContents)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.toGame().getIdOrZero()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            line.toGame().filterMin()
        }
    }
}