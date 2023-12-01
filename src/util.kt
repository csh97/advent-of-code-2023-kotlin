import java.lang.Exception

fun getInputString(filename: String) = object {}.javaClass.getResourceAsStream("$filename.txt")?.bufferedReader()?.readText() ?: throw Exception("Cant get input from file")

fun getInputLines(filename: String) = object {}.javaClass.getResourceAsStream("$filename.txt")?.bufferedReader()?.readLines() ?: throw Exception("Cant get input from file")