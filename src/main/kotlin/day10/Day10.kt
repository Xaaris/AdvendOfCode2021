package day10

import readInput

val opening = listOf('(', '[', '{', '<')
val closing = listOf(')', ']', '}', '>')
val closingToOpeningBrace = closing.zip(opening).toMap()
val openingToClosingBrace = opening.zip(closing).toMap()

fun solve1(input: List<String>): Int {
    val scores = closing.zip(listOf(3, 57, 1197, 25137)).toMap()
    val illegalChars = mutableListOf<Char>()
    for (line in input) {
        val openBraces = mutableListOf<Char>()
        line@ for (char in line) {
            if (char in opening) {
                openBraces.add(char)
            } else {
                val correspondingOpeningBrace = closingToOpeningBrace[char]!!
                if (openBraces.last() == correspondingOpeningBrace) {
                    openBraces.removeLast()
                } else {
                    illegalChars.add(char)
                    break@line
                }
            }
        }
    }
    return illegalChars.mapNotNull { scores[it] }.sum()
}

fun solve2(input: List<String>): Long {
    val scores = closing.zip(listOf(1, 2, 3, 4)).toMap()
    val incompleteLines = input.filter { it.isNotCorrupted() }
    val allCompletingChars = mutableListOf<List<Char>>()
    for (line in incompleteLines) {
        val completingCharsForLine = mutableListOf<Char>()
        val mutableLine = line.toMutableList()
        while (mutableLine.isNotEmpty()) {
            val last = mutableLine.last()
            mutableLine.removeLast()
            if (last in closing) {
                mutableLine.removeAt(mutableLine.lastIndexOf(closingToOpeningBrace[last]))
            } else {
                completingCharsForLine.add(openingToClosingBrace[last]!!)
            }
        }
        allCompletingChars.add(completingCharsForLine)
    }

    val scoresPerLine = allCompletingChars.map { line ->
        line.map { char -> scores[char]!!.toLong() }
            .reduce { acc, score -> acc * 5 + score }
    }

    return scoresPerLine.sorted()[scoresPerLine.size / 2]
}

fun String.isNotCorrupted(): Boolean {
    val openBraces = mutableListOf<Char>()
    for (char in this) {
        if (char in opening) {
            openBraces.add(char)
        } else {
            val correspondingOpeningBrace = closingToOpeningBrace[char]!!
            if (openBraces.last() == correspondingOpeningBrace) {
                openBraces.removeLast()
            } else {
                return false
            }
        }
    }
    return true
}


fun main() {
    val path = "day10/"
    val testInput = readInput("${path}input_test.txt")
    val input = readInput("${path}input.txt")
    check(solve1(testInput) == 26397)
    println(solve1(input))

    check(solve2(testInput) == 288957L)
    println(solve2(input))
}
