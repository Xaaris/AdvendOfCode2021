package day12

import readInput


fun solve1(input: List<String>): Int {
    val adjacencyMap = buildAdjacencyMap(input)
    println(adjacencyMap)
    val path = exploreCaves1(adjacencyMap)

    return path.size
}

fun solve2(input: List<String>): Int {
    val adjacencyMap = buildAdjacencyMap(input)
    println(adjacencyMap)
    val path = exploreCaves2(adjacencyMap)
    path.forEach { println(it.joinToString(",")) }
    return path.size
}

fun exploreCaves1(adjacencyMap: Map<String, List<String>>): List<List<String>> {
    val allPath: MutableList<List<String>> = mutableListOf()
    val queue: MutableList<List<String>> = mutableListOf(listOf("start"))
    while (queue.isNotEmpty()) {
        val currentPath = queue.removeAt(0)
        val lastCave = currentPath.last()
        if (lastCave == "end") {
            allPath.add(currentPath)
        }
        for (connectedCave in adjacencyMap[lastCave]!!) {
            if (connectedCave.isBigCave() || connectedCave !in currentPath) {
                val newPath = currentPath + connectedCave
                queue.add(newPath)
            }
        }
    }
    return allPath
}

fun exploreCaves2(adjacencyMap: Map<String, List<String>>): List<List<String>> {
    val allPath: MutableList<List<String>> = mutableListOf()
    val queue: MutableList<List<String>> = mutableListOf(listOf("start"))
    while (queue.isNotEmpty()) {
        val currentPath = queue.removeAt(0)
        val lastCave = currentPath.last()
        if (lastCave == "end") {
            allPath.add(currentPath)
        }
        for (connectedCave in adjacencyMap[lastCave]!!) {
            if (connectedCave.isBigCave()
                || connectedCave !in currentPath
                || connectedCave.isFirstSmallCaveVisitedTwiceIn(currentPath)
            ) {
                val newPath = currentPath + connectedCave
                queue.add(newPath)
            }
        }
    }
    return allPath
}

private fun String.isFirstSmallCaveVisitedTwiceIn(currentPath: List<String>): Boolean {
    if (isBigCave()) return false
    if (this == "start") return false
    if (this == "end") return false
    return currentPath.filter { it.isSmallCave() }.groupingBy { it }.eachCount().filterValues { it == 2 }.isEmpty()
}

fun String.isSmallCave() = first().isLowerCase()
fun String.isBigCave() = first().isUpperCase()

fun buildAdjacencyMap(input: List<String>): Map<String, List<String>> {
    val adjacencyMap = mutableMapOf<String, MutableList<String>>()
    input.map { it.split("-") }
        .map {
            adjacencyMap.getOrPut(it[0]) { mutableListOf() }.add(it[1])
            adjacencyMap.getOrPut(it[1]) { mutableListOf() }.add(it[0])
        }
    return adjacencyMap
}

fun main() {
    val path = "day12/"
    val testInput = readInput("${path}input_test.txt")
    val testInput2 = readInput("${path}input_test2.txt")
    val testInput3 = readInput("${path}input_test3.txt")
    val input = readInput("${path}input.txt")
    check(solve1(testInput) == 10)
    println(solve1(input))

    check(solve2(testInput) == 36)
    check(solve2(testInput2) == 103)
    check(solve2(testInput3) == 3509)
    println(solve2(input))
}
