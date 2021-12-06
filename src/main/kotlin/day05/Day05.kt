package day05

import java.io.File
import kotlin.math.abs


class Day05 {
    private val inputPath = "/day05/input.txt"
    private val url = Day05::class.java.getResource(inputPath)!!
    private val inputFile = File(url.file)

    fun solve1(): Int {
        val lines = readInput()
        val filteredLines = lines.filter { it.isHorizontal() or it.isVertical() }
        val allPoints = filteredLines.map { getAllPointsOnLine(it) }.flatten()
        val duplicatePoints: Map<Pair<Int, Int>, Int> = allPoints.groupingBy { it }.eachCount().filter { it.value > 1 }
        return duplicatePoints.size
    }

    fun solve2(): Int {
        val lines = readInput()
        val filteredLines = lines.filter { it.isHorizontal() or it.isVertical() or it.isDiagonal() }
        val allPoints = filteredLines.map { getAllPointsOnLine(it) }.flatten()
        val duplicatePoints: Map<Pair<Int, Int>, Int> = allPoints.groupingBy { it }.eachCount().filter { it.value > 1 }
        return duplicatePoints.size
    }

    private fun getAllPointsOnLine(line: Line): List<Pair<Int, Int>> {
        val points: MutableList<Pair<Int, Int>> = mutableListOf()
        if (line.isDiagonal()) {
            val xRange = if (line.x1 < line.x2) line.x1..line.x2 else line.x1 downTo line.x2
            val yRange = if (line.y1 < line.y2) line.y1..line.y2 else line.y1 downTo line.y2
            xRange.zip(yRange).forEach { points.add(it) }
        } else {
            for (x in minOf(line.x1, line.x2)..maxOf(line.x1, line.x2)) {
                for (y in minOf(line.y1, line.y2)..maxOf(line.y1, line.y2))
                    points.add(x to y)
            }
        }
        return points
    }

    private fun readInput(): List<Line> {
        return inputFile.readLines()
            .map { it.split(" -> ") }
            .map { it.map { tuple -> tuple.split(",") } }
            .map { it.flatten() }
            .map { it.map { coord -> coord.toInt() } }
            .map { (x1, y1, x2, y2) -> Line(x1, y1, x2, y2) }
    }

}

data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {

    fun isHorizontal() = x1 == x2

    fun isVertical() = y1 == y2

    fun isDiagonal() = abs(x1 - x2) == abs(y1 - y2)
}

fun main() {
    println(Day05().solve1())
    println(Day05().solve2())
}
