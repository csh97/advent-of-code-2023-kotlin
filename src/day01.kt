fun main() {
    val input = getInputLines("day01")
    println("Part 1 answer: ${part1(input)}")//54877
    println("Part 2 answer: ${part2(input)}")//54100
}

private fun part1(input: List<String>): Int {
    return input.sumOf { it.filter { it.isDigit() }.run { "${first()}${last()}".toInt() } }
}

private fun part2(input: List<String>): Int {
    val numberMap = mapOf("one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9")
    val nums = numberMap.keys

    val newInput = input.map { line ->
        val convertedLine = numberMap.entries.fold(line) { acc, entry ->
            acc.replace(entry.value, entry.key)
        }

        var newString = ""
        convertedLine.forEachIndexed { i, _ ->
            for (j in i + 1..convertedLine.length) {
                val subString = convertedLine.subSequence(i, j)
                if (nums.contains(subString)) {
                    newString += numberMap[subString]
                }
            }
        }
        newString
    }

    return part1(newInput)
}