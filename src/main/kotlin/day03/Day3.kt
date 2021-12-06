package day03

import java.io.File


class Day03 {
    private val inputPath = "/day03/input.txt"
    private val url = Day03::class.java.getResource(inputPath)!!
    private val inputFile = File(url.file)

    fun solve1(): Int {
        val input: List<List<Int>> = readInput()
        val averageValues: List<Double> = getAverageValuesPerBit(input)

        val gamma = averageValues
            .map {
                when (it > 0) {
                    true -> 1
                    false -> 0
                }
            }
            .joinToString("").toInt(2)
        val epsilon = averageValues
            .map {
                when (it < 0) {
                    true -> 1
                    false -> 0
                }
            }
            .joinToString("").toInt(2)
        return gamma * epsilon
    }

    fun solve2(): Int {
        val input: List<List<Int>> = readInput()
        val oxygenGeneratorRating = getOxygenGeneratorRating(input)
        val co2ScrubberRating = getCo2ScrubberRating(input)

        return  oxygenGeneratorRating * co2ScrubberRating
    }

    private fun getOxygenGeneratorRating(input: List<List<Int>>): Int {
        val binary = filter(input, 0, this::getMostCommonBitAtPosition).first()
        return binary.joinToString("").toInt(2)
    }

    private fun getCo2ScrubberRating(input: List<List<Int>>): Int {
        val binary = filter(input, 0, this::getLeastCommonBitAtPosition).first()
        return binary.joinToString("").toInt(2)
    }

    private fun filter(input: List<List<Int>>, position: Int, getFilterBitFunction: (List<List<Int>>, Int) -> Int): List<List<Int>> {
        if (input.size == 1) {
            return input
        }
        val filterBit: Int = getFilterBitFunction(input, position)
        val filteredInput = input.filter { it[position] == filterBit }
        return filter(filteredInput, position + 1, getFilterBitFunction)
    }

    private fun getMostCommonBitAtPosition(input: List<List<Int>>, position: Int): Int {
        return if (input.sumOf { it[position] } >= input.size / 2.0) 1 else 0
    }

    private fun getLeastCommonBitAtPosition(input: List<List<Int>>, position: Int): Int {
        return if (input.sumOf { it[position] } >= input.size / 2.0) 0 else 1
    }

    private fun getAverageValuesPerBit(input: List<List<Int>>) = input
        .map { bits -> bits.map { bit -> bit.toDouble() } }
        .reduce { bits, result -> result.zip(bits).map { (a, b) -> a + b - 0.5 } }

    private fun readInput(): List<List<Int>> = inputFile.readLines()
        .map { line -> line.map { char -> char.digitToInt() } }
}


fun main() {
    println(Day03().solve1())
    println(Day03().solve2())
}
