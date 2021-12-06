package day02

import day02.Direction.*
import java.io.File


private const val inputPath = "/day02/input.txt"

class Day02 {

    fun solve1(): Int {
        val url = Day02::class.java.getResource(inputPath)!!
        val submarine = Submarine1()
        File(url.file)
            .readLines()
            .map { it.toCommand() }
            .forEach { submarine.execute(it) }
        return submarine.calculatePositionResult()
    }

    fun solve2(): Int {
        val submarine = Submarine2()
        val url = Day02::class.java.getResource(inputPath)!!
        File(url.file)
            .readLines()
            .map { it.toCommand() }
            .forEach { submarine.execute(it) }
        return submarine.calculatePositionResult()
    }

}

fun String.toCommand(): Command {
    val (directionString, valueString) = this.split(" ")
    return Command(valueOf(directionString.uppercase()), valueString.toInt())

}

class Submarine1 {
    private var depth = 0
    private var horizontalPosition = 0

    fun execute(command: Command) {
        when (command.direction) {
            FORWARD -> horizontalPosition += command.value
            UP -> depth -= command.value
            DOWN -> depth += command.value
        }
    }

    fun calculatePositionResult() = depth * horizontalPosition
}

class Submarine2 {
    private var aim = 0
    private var depth = 0
    private var horizontalPosition = 0

    fun execute(command: Command) {
        when (command.direction) {
            FORWARD -> {
                horizontalPosition += command.value
                depth += aim * command.value
            }
            UP -> aim -= command.value
            DOWN -> aim += command.value
        }
    }

    fun calculatePositionResult() = depth * horizontalPosition
}

data class Command(val direction: Direction, val value: Int)

enum class Direction {
    FORWARD, UP, DOWN
}

fun main() {
    println(Day02().solve1())
    println(Day02().solve2())
}
