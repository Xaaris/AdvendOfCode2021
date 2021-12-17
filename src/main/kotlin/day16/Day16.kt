package day16

import readInput


fun solve1(input: List<String>): Int {
    val hex = input.first()
    println(hex)
    val bigInt = hex.toBigInteger(16)
    println(bigInt)
    val binaryInput = bigInt.toString(2)
    println(binaryInput)
    TODO()
}

fun solve2(input: List<String>): Int {
    TODO()
}

fun main() {
    val path = "day16/"
    val testInput = readInput("${path}input_test.txt")
    val input = readInput("${path}input.txt")
    check(solve1(testInput) == 40)
    println(solve1(input))

    check(solve2(testInput) == 315)
    println(solve2(input))
}
