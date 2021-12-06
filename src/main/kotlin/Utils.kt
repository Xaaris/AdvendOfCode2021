import java.io.File

fun readInput(path: String): List<String> {
    val url = {}::class.java.getResource(path)!!
    val inputFile = File(url.file)
    return inputFile.readLines()
}