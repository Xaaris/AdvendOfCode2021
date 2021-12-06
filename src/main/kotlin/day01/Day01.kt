package day01

import java.io.File


private const val inputPath = "/day01/input.txt"

class Day01 {

    fun solve1(): Int {
        val url = Day01::class.java.getResource(inputPath)!!
        return File(url.file)
            .readLines()
            .map { it.toInt() }
            .zipWithNext { a, b -> b > a }
            .count { it }
    }

    fun solve2(): Int {
        val url = Day01::class.java.getResource(inputPath)!!
        return File(url.file)
            .readLines()
            .map { it.toInt() }
            .windowed(3)
            .zipWithNext { a, b -> b.sum() > a.sum() }
            .count { it }
    }

}

fun main() {
    println(Day01().solve1())
    println(Day01().solve2())
}
