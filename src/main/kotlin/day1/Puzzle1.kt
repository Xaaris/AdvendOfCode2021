package day1

import java.io.File


class Day1 {

    fun solve1(): Int {
        val path = "/day1/input.txt"
        val url = Day1::class.java.getResource(path)!!
        return File(url.file)
            .readLines()
            .map { it.toInt() }
            .zipWithNext { a, b -> b > a }
            .count { it }
    }

    fun solve2(): Int {
        val path = "/day1/input.txt"
        val url = Day1::class.java.getResource(path)!!
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
