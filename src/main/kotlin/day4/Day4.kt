package day4

import java.io.File


class Day4 {
    private val inputPath = "/day4/input.txt"
    private val url = Day4::class.java.getResource(inputPath)!!
    private val inputFile = File(url.file)

    fun solve1(): Int {
        val (drawnNumbers, boards) = readInput()
        for (drawnNumber in drawnNumbers) {
            for (board in boards) {
                board.markNumber(drawnNumber)
                if (board.checkBingo()) {
                    return board.calculateScore(drawnNumber)
                }
            }
        }
        throw Exception("Didn't find a bingo board")
    }

    fun solve2(): Int {
        var (drawnNumbers, boards) = readInput()
        for (drawnNumber in drawnNumbers) {
            boards.forEach { it.markNumber(drawnNumber) }
            if (boards.size > 1) {
                boards = boards.filter { !it.checkBingo() }
            } else {
                return boards.first().calculateScore(drawnNumber)
            }
        }
        throw Exception("Didn't find a bingo board")
    }


    private fun readInput(): Pair<List<Int>, List<Board>> {
        val inputLines = inputFile.readLines()
        val drawnNumbers: List<Int> = readDraws(inputLines.first())
        val boards = inputLines.drop(2)
            .windowed(5, 6)
            .map { lines ->
                lines.map { line ->
                    line.trim().split("\\s+".toRegex())
                        .map { it.toInt() }
                }
            }
            .map { Board(it) }
        return drawnNumbers to boards
    }

    private fun readDraws(line: String): List<Int> {
        return line.split(",").map { it.toInt() }
    }
}

data class Board(var board: List<List<Int?>>) {

    var mutableBoard: MutableList<MutableList<Int?>> = board.map { it.toMutableList() }.toMutableList()

    fun markNumber(drawnNumber: Int) {
        for (row in 0 until mutableBoard.size) {
            for (col in 0 until mutableBoard[0].size) {
                if (mutableBoard[row][col] == drawnNumber) {
                    mutableBoard[row][col] = null
                }
            }
        }
    }

    fun checkBingo(): Boolean {
        // Check rows
        for (row in 0 until mutableBoard.size) {
            if (mutableBoard[row].all { it == null }) {
                return true
            }
        }
        // Check columns
        for (col in 0 until mutableBoard[0].size) {
            val columnValues: MutableList<Int?> = emptyList<Int?>().toMutableList()
            for (row in 0 until mutableBoard.size) {
                columnValues.add(mutableBoard[row][col])
            }
            if (columnValues.all { it == null }) {
                return true
            }
        }
        return false
    }

    fun calculateScore(drawnNumber: Int): Int {
        return mutableBoard.flatten().filterNotNull().sum() * drawnNumber
    }
}


fun main() {
    println(Day4().solve1())
    println(Day4().solve2())
}
