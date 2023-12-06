fun main() {
    val input = getInputLines("day06")
    println("Part 1 answer: ${part1(input)}")//2756160
    println("Part 2 answer: ${part2(input)}")//34788142
}

private fun part1(input: List<String>): Int {
    val times = input.first().getNums("Time").map { it.trim().toLong() }
    val distances = input[1].getNums("Distance").map { it.trim().toLong() }
    return times.mapIndexed { i, time -> findOptimalTimes(time, distances[i]) }.reduce(Int::times)
}

private fun part2(input: List<String>): Int {
    val time = input.first().getNums("Time").joinToString("").toLong()
    val distance = input[1].getNums("Distance").joinToString("").toLong()
    return findOptimalTimes(time, distance)
}

private fun String.getNums(field: String) = substringAfter("$field:").trim().split(" ").filterNot { it == "" }

private fun findOptimalTimes(time: Long, distance: Long) =
    (1..time).fold(0) { acc, timeHeld ->
        val timeLeft = time - timeHeld
        if (timeLeft * timeHeld > distance) {
            acc + 1
        } else {
            acc
        }
    }