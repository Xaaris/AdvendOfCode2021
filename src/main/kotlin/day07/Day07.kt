package day07

import readInput
import kotlin.math.abs

fun solve1(input: List<Int>): Int {
    return (input.minOrNull()!!..input.maxOrNull()!!)
        .minOf { endPos -> input.sumOf { beginPos -> abs(beginPos - endPos) } }
}


fun solve2(input: List<Int>): Int {
    return (input.minOrNull()!!..input.maxOrNull()!!)
        .minOf { endPos ->
            input.map { beginPos -> abs(beginPos - endPos) }
                .sumOf { diff -> diff * (diff + 1) / 2 }
        }
}

fun main() {
    val path = "day07/"
    val testInput = readInput("${path}input_test.txt").toInts()
    val input = readInput("${path}input.txt").toInts()
    check(solve1(testInput) == 37)
    println(solve1(input))

    check(solve2(testInput) == 168)
    println(solve2(input))
}

fun List<String>.toInts() = first().split(",").map { it.toInt() }