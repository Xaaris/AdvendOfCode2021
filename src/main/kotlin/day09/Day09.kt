package day09

import readInput

fun solve1(input: List<List<Int>>): Int {
    val riskLevels = findBasins(input).map { (row, col) -> input[row][col] + 1 }
    return riskLevels.sum()
}

fun solve2(input: List<List<Int>>): Int {
    val basins = findBasins(input)
    val (first, second, third) = basins.map { exploreBasin(input, it) }
        .map { it.size }
        .sortedDescending()
        .take(3)

    return first * second * third
}

fun exploreBasin(input: List<List<Int>>, basin: Pair<Int, Int>): Set<Pair<Int, Int>> {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val queue: MutableList<Pair<Int, Int>> = mutableListOf(basin)
    while (queue.isNotEmpty()) {
        val location = queue.removeAt(0)
        val value = input[location.first][location.second]
        if (location !in visited && value != 9) {
            queue.addAll(getNeighbors(input, location)
                .filter { it.isNotNine(input) })
            visited.add(location)
        }
    }
    return visited
}

fun Pair<Int, Int>.isNotNine(input: List<List<Int>>) = input[first][second] < 9

fun getNeighbors(input: List<List<Int>>, location: Pair<Int, Int>): List<Pair<Int, Int>> {
    val width = input.size - 1
    val height = input.first().size - 1
    val neighbors = mutableListOf<Pair<Int, Int>>()
    val (row, col) = location
    if (row > 0) neighbors.add(row - 1 to col)
    if (row < width) neighbors.add(row + 1 to col)
    if (col > 0) neighbors.add(row to col - 1)
    if (col < height) neighbors.add(row to col + 1)
    return neighbors
}

fun findBasins(input: List<List<Int>>): List<Pair<Int, Int>> {
    val width = input.size - 1
    val height = input.first().size - 1
    val basins = mutableListOf<Pair<Int, Int>>()
    for (row in 0..width) {
        for (col in 0..height) {
            val currentValue = input[row][col]
            val surroundingValues = mutableListOf<Int>()
            if (row > 0) surroundingValues.add(input[row - 1][col])
            if (row < width) surroundingValues.add(input[row + 1][col])
            if (col > 0) surroundingValues.add(input[row][col - 1])
            if (col < height) surroundingValues.add(input[row][col + 1])
            if (surroundingValues.all { it > currentValue }) {
                basins.add(row to col)
            }
        }
    }
    return basins
}

fun main() {
    val path = "day09/"
    val testInput = readInput("${path}input_test.txt").toInts()
    val input = readInput("${path}input.txt").toInts()
    check(solve1(testInput) == 15)
    println(solve1(input))

    check(solve2(testInput) == 1134)
    println(solve2(input))
}

fun List<String>.toInts() = map { line -> line.map { digit -> digit.digitToInt() } }