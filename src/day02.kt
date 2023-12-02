fun main() {
    val input = getInputLines("day02")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

enum class Colour { RED, GREEN, BLUE }

private fun part1(input: List<String>): Int {
    return input.foldIndexed(0) { i, acc, line ->
        if (line.contains("(1[3-9]|[2-9][0-9]) red".toRegex()) ||
            line.contains("(1[4-9]|[2-9][0-9]) green".toRegex()) ||
            line.contains("(1[5-9]|[2-9][0-9]) blue".toRegex())
        ) acc else acc + (i + 1)
    }
}

private fun part2(input: List<String>): Int {
    return input.sumOf { line ->
        Colour.entries.map { line.findMinForColour(it.name.lowercase()) }.reduce(Int::times)
    }
}

fun String.findMinForColour(colour: String): Int {
    val match = Regex("(\\d+ $colour)").findAll(this)
    return match.map { it.value.substringBefore(" $colour").toInt() }.max()
}