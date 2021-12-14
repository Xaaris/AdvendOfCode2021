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
    var template = input.first()
    val insertionRules = input.drop(2).map { it.split(" -> ") }.associate { it[0] to it[1] }
    for (step in 1..steps) {
        println(step)
        println(template)
        println(template.groupingBy { it }.eachCount())
        template = template.windowed(2).joinToString("") { it[0] + insertionRules[it]!! } + template.last()
    }
    val countOfChars: Map<Char, Int> = template.groupingBy { it }.eachCount()
    return countOfChars.maxOf { it.value } - countOfChars.minOf { it.value }
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
