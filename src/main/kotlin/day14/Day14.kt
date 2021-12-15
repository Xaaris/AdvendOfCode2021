package day14

import readInput


fun solve1(input: List<String>, steps: Int): Int {
    var template = input.first()
    val insertionRules = input.drop(2).map { it.split(" -> ") }.associate { it[0] to it[1] }
    for (step in 1..steps) {
        template = template.windowed(2).joinToString("") { it[0] + insertionRules[it]!! } + template.last()
    }
    val countOfChars: Map<Char, Int> = template.groupingBy { it }.eachCount()
    return countOfChars.maxOf { it.value } - countOfChars.minOf { it.value }
}

fun solve2(input: List<String>, steps: Int): Long {
    val template = input.first()
    val insertionRules = input.drop(2).map { it.split(" -> ") }.associate { it[0] to it[1] }

    var pairCounter = template.windowed(2).associateWith { 1L }
    for (step in 1..steps) {
        pairCounter = calcNextStep(pairCounter, insertionRules)
    println(pairCounter)
    }
    val charCounter = mutableMapOf<Char, Long>()
    for ((pair, count) in pairCounter) {
        charCounter.merge(pair.first(), count, Long::plus)
    }
    charCounter.merge(template.last(), 1, Long::plus)
    println(charCounter)
    val max = charCounter.maxOf { it.value }
    val min = charCounter.minOf { it.value }

    println("max $max")
    println("min $min")
    return max - min
}

private fun calcNextStep(
    pairCounter: Map<String, Long>,
    insertionRules: Map<String, String>
): MutableMap<String, Long> {
    val newPairs = mutableMapOf<String, Long>()
    for ((pair, count) in pairCounter) {
        val newChar = insertionRules[pair]!!
        newPairs.merge(pair.first() + newChar, count, Long::plus)
        newPairs.merge(newChar + pair.last(), count, Long::plus)
    }
    return newPairs
}


fun main() {
    val path = "day14/"
    val testInput = readInput("${path}input_test.txt")
    val input = readInput("${path}input.txt")
    check(solve1(testInput, 10) == 1588)
    println(solve1(input, 10))

    check(solve2(testInput, 40) == 2188189693529)
    println(solve2(input, 40))
}
