package day13

import readInput


fun solve1(input: List<String>): Int {
    val dots = readInDots(input)
    val folds = readInFolds(input)
    val (direction, line) = folds.first()
    val dotsAfterFold = fold(dots, direction, line)
    return dotsAfterFold.size
}

fun solve2(input: List<String>) {
    var dots = readInDots(input)
    val folds = readInFolds(input)
    for ((direction, line) in folds) {
        dots = fold(dots, direction, line)
        printDots(dots)
    }
}

fun printDots(dots: Set<Pair<Int, Int>>) {
    val maxX = dots.maxOf { it.first }
    val maxY = dots.maxOf { it.second }
    val paper = MutableList(maxY + 1) { MutableList(maxX + 1) { "." } }
    for ((x, y) in dots) {
        paper[y][x] = "#"
    }
    paper.forEach { println(it.joinToString("")) }
    println("\n\n")
}

private fun readInDots(input: List<String>): Set<Pair<Int, Int>> {
    return input.takeWhile { it.isNotEmpty() }
        .map { it.split(",") }
        .map { (x, y) -> x.toInt() to y.toInt() }.toSet()
}

private fun readInFolds(input: List<String>): List<Pair<String, Int>> {
    return input.dropWhile { it.isNotEmpty() }
        .drop(1)
        .map { it.substringAfter("fold along ").split("=") }
        .map { (direction, line) -> direction to line.toInt() }
}

private fun fold(dots: Set<Pair<Int, Int>>, direction: String, line: Int): Set<Pair<Int, Int>> {
    val dotsAfterFold = mutableSetOf<Pair<Int, Int>>()
    if (direction == "y") {
        for (dot in dots) {
            if (dot.second > line) {
                val diff = dot.second - line
                dotsAfterFold.add(dot.first to dot.second - 2 * diff)
            } else {
                dotsAfterFold.add(dot)
            }
        }
    } else {
        for (dot in dots) {
            if (dot.first > line) {
                val diff = dot.first - line
                dotsAfterFold.add(dot.first - 2 * diff to dot.second)
            } else {
                dotsAfterFold.add(dot)
            }
        }
    }
    return dotsAfterFold
}

fun main() {
    val path = "day13/"
    val testInput = readInput("${path}input_test.txt")
    val input = readInput("${path}input.txt")
    check(solve1(testInput) == 17)
    println(solve1(input))

    solve2(testInput)
    solve2(input)
}
