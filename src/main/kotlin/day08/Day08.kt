package day08

import readInput

fun solve1(input: List<String>): Int {
    return input.map { it.substringAfter("|") }
        .map { it.split(" ") }
        .flatten()
        .count { it.length in listOf(2, 4, 3, 7) }
}


fun solve2(input: List<String>): Int {
    val signalsToOutputs: List<Pair<List<String>, List<String>>> = input.map { it.split("|") }
        .map { (signals, outputs) -> signals.trim().split(" ") to outputs.trim().split(" ") }

    val finalOutputValues = mutableListOf<Int>()
    for ((signal, output) in signalsToOutputs) {
        val wireToSegment: MutableMap<Char, Char> = mutableMapOf()
        val charToFrequency: Map<Char, Int> = signal.joinToString("").groupingBy { it }.eachCount().toMap()
        val four = signal.find { it.length == 4 }!!
        val one = signal.find { it.length == 2 }!!
        for ((char, freq) in charToFrequency) {
            // a = 8
            // b = 6
            // c = 8
            // d = 7
            // e = 4
            // f = 9
            // g = 7
            when (freq) {
                6 -> wireToSegment[char] = 'b'
                4 -> wireToSegment[char] = 'e'
                9 -> wireToSegment[char] = 'f'
                7 -> if (char in four) {
                    wireToSegment[char] = 'd'
                } else {
                    wireToSegment[char] = 'g'
                }
                8 -> if (char in one) {
                    wireToSegment[char] = 'c'
                } else {
                    wireToSegment[char] = 'a'
                }
            }
        }
        println(wireToSegment)
        val convertedOutput = output.map { number -> number.map { wireToSegment[it] }.sortedBy { it }.joinToString("") }
        println(convertedOutput)
        val finalOutput = convertedOutput.map { getNumberBySegments(it) }
            .joinToString("")
            .toInt()
        finalOutputValues.add(finalOutput)
        println(finalOutput)

    }

    return finalOutputValues.sum()
}

fun getNumberBySegments(segments: String): Int {
    return when (segments) {
        "abcefg" -> 0
        "cf" -> 1
        "acdeg" -> 2
        "acdfg" -> 3
        "bcdf" -> 4
        "abdfg" -> 5
        "abdefg" -> 6
        "acf" -> 7
        "abcdefg" -> 8
        "abcdfg" -> 9

        else -> throw Exception("Something is wrong")
    }
}

fun main() {
    val path = "day08/"
    val testInput = readInput("${path}input_test.txt")
    val input = readInput("${path}input.txt")
    check(solve1(testInput) == 26)
    println(solve1(input))

    check(solve2(testInput) == 61229)
    println(solve2(input))
}
