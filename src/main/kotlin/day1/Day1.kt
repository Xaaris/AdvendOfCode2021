package day1

import java.io.File


private const val inputPath = "/day1/input.txt"

class Day1 {

    fun solve1(): Int {
        val url = Day1::class.java.getResource(inputPath)!!
        return File(url.file)
            .readLines()
            .map { it.toInt() }
            .zipWithNext { a, b -> b > a }
            .count { it }
    }

    fun solve2(): Int {
        val url = Day1::class.java.getResource(inputPath)!!
        return File(url.file)
            .readLines()
            .map { it.toInt() }
            .windowed(3)
            .zipWithNext { a, b -> b.sum() > a.sum() }
            .count { it }
    }

}

fun main() {
    println(Day1().solve1())
    println(Day1().solve2())
}
