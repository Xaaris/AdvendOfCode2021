package day06

import readInput

fun solve1(input: List<String>, days: Int): Int {
    val startPopulation = input.first().split(",").map { it.toInt() }
    val endPopulation = evolve(startPopulation, days)
    return endPopulation.size
}

fun solve2(input: List<String>, days: Int): Long {
    val startPopulation = input.first().split(",").map { it.toInt() }
    val descendantsPerAge: Map<Int, Long> = startPopulation.groupingBy { it }.eachCount().map { it.key to it.value.toLong() }.toMap()
    val finalDescendants = calculateDescendants(descendantsPerAge, 0, days)
    return finalDescendants.values.sum()
}

fun calculateDescendants(initialDescendants: Map<Int, Long>, day: Int, finalDay: Int): Map<Int, Long> {
    if (day == finalDay) return initialDescendants
    val newDescendants: MutableMap<Int, Long> = (0..8).associateWith { 0L }.toMutableMap()
    (0..8).map {
        if (it == 0) {
            newDescendants[8] = initialDescendants.getOrDefault(0,0)
            newDescendants.merge(6, initialDescendants.getOrDefault(0,0), Long::plus)
        } else {
            newDescendants.merge(it - 1, initialDescendants.getOrDefault(it,0), Long::plus)
        }
    }
    return calculateDescendants(newDescendants, day +1, finalDay)
}

fun evolve(initialPopulation: List<Int>, daysLeft: Int): List<Int> {
    if (daysLeft == 0) {
        return initialPopulation
    }
    val newPopulation = mutableListOf<Int>()
    for (fish in initialPopulation) {
        if (fish == 0) {
            newPopulation.add(6)
            newPopulation.add(8)
        } else {
            newPopulation.add(fish - 1)
        }
    }
    return evolve(newPopulation, daysLeft - 1)
}


fun main() {
    val testInput = readInput("day06/input_test.txt")
    val input = readInput("day06/input.txt")
    check(solve1(testInput, 18) == 26)
    check(solve1(testInput, 80) == 5934)
    println(solve1(input, 80))

    check(solve2(testInput, 18) == 26L)
    check(solve2(testInput, 80) == 5934L)
    println(solve2(input, 256))
}
