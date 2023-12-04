import kotlin.math.pow

fun main() {
    val input = getInputLines("day04")
    println("Part 1 answer: ${part1(input)}")//21568
    println("Part 2 answer: ${part2(input)}")//11827296
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
        findAllCardsForCard(card, input, 0)
    }
}

fun findAllCardsForCard(card: String, input: List<String>, totalCards: Int): Int {
    var totalCardsBranch = totalCards
    val i = card.split(":")[0].substringAfter("Card ").trim().toInt()
    val matches = card.findMatches()
    if (matches > 0) {
        input.subList(i, i + matches).forEach {
            totalCardsBranch += findAllCardsForCard(it, input, totalCards)
        }
    }
    return totalCardsBranch + 1
}

fun String.findMatches(): Int {
    val (winningNums, nums) = split("|")
    val winningNumsSplit = winningNums.split(":")[1].trim().split(" ").filterNot { it == "" }
    return nums.split(" ").filterNot { it == "" }.filter { winningNumsSplit.contains(it) }.size
}