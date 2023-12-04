import kotlin.math.pow
import kotlin.time.measureTimedValue

fun main() {
    val input = getInputLines("day04")
    println("Part 1 answer: ${part1(input)}")//21568
    val (value, timeTaken) = measureTimedValue { part2(input) }
    println("Part 2 answer: $value - Took ${timeTaken.inWholeMilliseconds}ms")//11827296
}

private fun part1(input: List<String>): Int {
    val part1 = input.sumOf { card ->
        val matches = card.findMatches()
        if (matches > 0) 2.0.pow((matches - 1).toDouble()).toInt() else 0
    }
    return part1
}

private fun part2(input: List<String>): Int {
    return input.sumOf { card ->
        findAllCardsForCard(card, input)
    }
}

val cache = mutableMapOf<Int, Int>()
fun findAllCardsForCard(card: String, input: List<String>): Int {
    val i = card.split(":")[0].substringAfter("Card ").trim().toInt()
    val totalCardsBranch = cache.getOrPut(i) {
        val matches = card.findMatches()
        val total = if (matches > 0) {
            input.subList(i, i + matches).sumOf {
                findAllCardsForCard(it, input)
            }
        } else 0
        total + 1
    }
    return totalCardsBranch
}

fun String.findMatches(): Int {
    val (winningNums, nums) = split("|")
    val winningNumsSplit = winningNums.split(":")[1].trim().split(" ").filterNot { it == "" }
    return nums.split(" ").filterNot { it == "" }.filter { winningNumsSplit.contains(it) }.size
}