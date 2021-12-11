package day11

import readInput
import kotlin.math.max
import kotlin.math.min

const val width = 10
const val height = 10

fun solve1(inputFrozen: List<List<Int>>, steps: Int): Int {
    val input = inputFrozen.map { line -> line.toMutableList() }.toMutableList()
    var totalFlashes = 0
    for (step in 1..steps) {
        increaseEnergyLevel(input)
        flash(input)
        totalFlashes += countFlashes(input)
        reset(input)
        input.forEach { println(it) }
        println("step: $step, totalFlashes: $totalFlashes")
    }
    return totalFlashes
}

fun solve2(inputFrozen: List<List<Int>>): Int {
    val input = inputFrozen.map { line -> line.toMutableList() }.toMutableList()
    var step = 0
    while (!isSynchronized(input)) {
        step++
        increaseEnergyLevel(input)
        flash(input)
        reset(input)
        input.forEach { println(it) }
        println("step: $step")
    }
    return step
}

fun isSynchronized(input: List<List<Int>>): Boolean {
    return input.flatten().all { it == 0 }
}

private fun increaseEnergyLevel(
    input: MutableList<MutableList<Int>>,
    xRange: IntRange = 0 until width,
    yRange: IntRange = 0 until height
) {
    for (row in xRange) {
        for (col in yRange) {
            input[row][col] += 1
        }
    }
}

private fun flash(input: MutableList<MutableList<Int>>) {
    val flashed = MutableList(width + 1) { MutableList(height + 1) { false } }
    while (flashesLeft(input, flashed)) {
        for (row in 0 until width) {
            for (col in 0 until height) {
                if (input[row][col] > 9 && !flashed[row][col]) {
                    flashed[row][col] = true
                    val xRange = max(row - 1, 0)..min(row + 1, width - 1)
                    val yRange = max(col - 1, 0)..min(col + 1, height - 1)
                    increaseEnergyLevel(input, xRange, yRange)
                }
            }
        }
    }
}

fun countFlashes(input: MutableList<MutableList<Int>>): Int {
    return input.flatten().count { it > 9 }
}

fun reset(input: MutableList<MutableList<Int>>) {
    for (row in 0 until width) {
        for (col in 0 until height) {
            if (input[row][col] > 9)
                input[row][col] = 0
        }
    }
}

fun flashesLeft(input: List<List<Int>>, flashed: List<List<Boolean>>): Boolean {
    for (row in 0 until width) {
        for (col in 0 until height) {
            if (input[row][col] > 9 && !flashed[row][col])
                return true
        }
    }
    return false
}


fun main() {
    val path = "day11/"
    val testInput = readInput("${path}input_test.txt").toInts()
    val input = readInput("${path}input.txt").toInts()
    check(solve1(testInput, 100) == 1656)
    println(solve1(input, 100))

    check(solve2(testInput) == 195)
    println(solve2(input))
}

fun List<String>.toInts() = map { line -> line.map { digit -> digit.digitToInt() } }