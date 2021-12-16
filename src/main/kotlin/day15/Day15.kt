package day15

import readInput


fun solve1(input: List<List<Int>>): Int {
    val width = input.size
    val height = input.first().size
    val adjacencyMap = buildAdjacencyMap(input)
    val graph = Graph(adjacencyMap)
    val shortestPathTree = dijkstra(graph, 0 to 0)
    val shortestPath = shortestPath(shortestPathTree, 0 to 0, width - 1 to height - 1)
    return shortestPath.drop(1).sumOf { input[it.first][it.second] }
}

fun solve2(input: List<List<Int>>): Int {
    val expandedInput = expand(input)
    val adjacencyMap = buildAdjacencyMap(expandedInput)
    val graph = Graph(adjacencyMap)
    val shortestPathTree = dijkstra(graph, 0 to 0)
    val shortestPath = shortestPath(shortestPathTree, 0 to 0, expandedInput.size - 1 to expandedInput.first().size - 1)
    return shortestPath.drop(1).sumOf { expandedInput[it.first][it.second] }
}

fun expand(input: List<List<Int>>): List<List<Int>> {
    val width = input.size
    val height = input.first().size
    val expandedInput = MutableList(5 * width) { MutableList(5 * height) { 0 } }
    for (row in 0 until 5 * width) {
        for (col in 0 until 5 * height) {
            var value = input[row % width][col % height] + row / width + col / height
            if (value > 9) {
                value = value % 10 + 1
            }
            expandedInput[row][col] = value
        }
    }
    return expandedInput
}

private fun buildAdjacencyMap(input: List<List<Int>>): MutableMap<Pair<Pair<Int, Int>, Pair<Int, Int>>, Int> {
    val width = input.size
    val height = input.first().size
    val adjacencyMap = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Int>()
    for (row in 0 until width) {
        for (col in 0 until height) {
            val neighbors: List<Pair<Int, Int>> = getNeighbors(input, row to col)
            for (neighbor in neighbors) {
                val weight = input[neighbor.first][neighbor.second]
                adjacencyMap[row to col to neighbor] = weight
            }
        }
    }
    return adjacencyMap
}

fun <T> dijkstra(graph: Graph<T>, start: T): Map<T, T?> {
    val S: MutableSet<T> = mutableSetOf() // a subset of vertices, for which we know the true distance

    val delta: MutableMap<T, Int> = graph.vertices.associateWith { Int.MAX_VALUE }.toMutableMap()
    delta[start] = 0

    val previous: MutableMap<T, T?> = graph.vertices.associateWith { null }.toMutableMap()

    while (S != graph.vertices) {
        val v: T = delta.filter { !S.contains(it.key) }.minByOrNull { it.value }!!.key

        graph.edges.getValue(v).minus(S).forEach { neighbor ->
            val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))

            if (newPath < delta.getValue(neighbor)) {
                delta[neighbor] = newPath
                previous[neighbor] = v
            }
        }

        S.add(v)
    }

    return previous.toMap()
}

fun <T> shortestPath(shortestPathTree: Map<T, T?>, start: T, end: T): List<T> {
    fun pathTo(start: T, end: T): List<T> {
        if (shortestPathTree[end] == null) return listOf(end)
        return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
    }

    return pathTo(start, end)
}

fun getNeighbors(input: List<List<Int>>, location: Pair<Int, Int>): List<Pair<Int, Int>> {
    val width = input.size - 1
    val height = input.first().size - 1
    val neighbors = mutableListOf<Pair<Int, Int>>()
    val (row, col) = location
    if (row > 0) neighbors.add(row - 1 to col)
    if (row < width) neighbors.add(row + 1 to col)
    if (col > 0) neighbors.add(row to col - 1)
    if (col < height) neighbors.add(row to col + 1)
    return neighbors
}

fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(): Set<T> = this.map { (a, b) -> listOf(a, b) }.flatten().toSet()

fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(predicate: (T) -> Boolean): Set<T> =
    this.map { (a, b) -> listOf(a, b) }.flatten().filter(predicate).toSet()

data class Graph<T>(
    val vertices: Set<T>, val edges: Map<T, Set<T>>, val weights: Map<Pair<T, T>, Int>
) {
    constructor(weights: Map<Pair<T, T>, Int>) : this(
        vertices = weights.keys.toList().getUniqueValuesFromPairs(),
        edges = weights.keys.groupBy { it.first }.mapValues { it.value.getUniqueValuesFromPairs { x -> x != it.key } }
            .withDefault { emptySet() },
        weights = weights
    )
}


fun main() {
    val path = "day15/"
    val testInput = readInput("${path}input_test.txt").toInts()
    val input = readInput("${path}input.txt").toInts()
//    check(solve1(testInput) == 40)
//    println(solve1(input))

    check(solve2(testInput) == 315)
    println(solve2(input))
}

fun List<String>.toInts() = map { line -> line.map { digit -> digit.digitToInt() } }