package day1

import java.io.File


class Puzzle1 {

    fun solve(): Int {
        val path = "/day1/input.txt"
        val url = Puzzle1::class.java.getResource(path)!!
        return File(url.file)
            .readLines()
            .map { it.toInt() }
            .zipWithNext { a, b -> b > a }
            .count { it }
    }

}

class Puzzle2 {

    fun solve(): Int {
        val path = "/day1/input.txt"
        val url = Puzzle1::class.java.getResource(path)!!
        return File(url.file)
            .readLines()
            .map { it.toInt() }
            .windowed(3)
            .zipWithNext { a, b -> b.sum() > a.sum() }
            .count { it }
    }

}

fun main() {
    println(Puzzle1().solve())
    println(Puzzle2().solve())
}
