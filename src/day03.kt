fun main() {
    val input = getInputLines("day03")
    println("Part 1 answer: ${part1(input)}")//551094
    println("Part 2 answer: ${part2(input)}")//80179647
}

val numbersRegex = Regex("[0-9]+")

typealias Part = Pair<String, Int>
typealias Coord = Pair<Int, Int>
typealias PartNums = List<Int>

private fun part1(input: List<String>): Int {
    val specialCharRegex = Regex("[^A-Za-z0-9.]")
    return input.mapIndexed { i, line ->
        numbersRegex.findAllNumbersOnLine(line).filter {
            when {
                i > 0 && specialCharRegex.findOnLineForPart(input, i - 1, it).toList().isNotEmpty() -> true
                it.second > 0 && specialCharRegex.find("${line[it.second - 1]}")?.value != null -> true
                (it.second + it.first.length) < line.length && specialCharRegex.find("${line[it.second + it.first.length]}")?.value != null -> true
                i + 1 < input.size && specialCharRegex.findOnLineForPart(input, i + 1, it).toList().isNotEmpty() -> true
                else -> false
            }
        }.sumOf { it.first.toInt() }
    }.sum()
}

private fun part2(input: List<String>): Int {
    val gearRegex = Regex("\\*")
    val potentialGears = mutableMapOf<Coord, PartNums>()
    input.forEachIndexed { i, line ->
        val lineBefore = i - 1
        val lineAfter = i + 1
        val nums = numbersRegex.findAllNumbersOnLine(line)

        nums.forEach {
            val charBefore = it.second - 1
            val charAfter = it.second + it.first.length
            if (lineBefore >= 0) {//Above
                val specialChars = gearRegex.findOnLineForPart(input, lineBefore, it)
                specialChars.addPotentialGears(input, lineBefore, it, potentialGears)
            }

            if (charBefore >= 0) {//Before
                val isGear = line[charBefore] == '*'
                if (isGear) potentialGears[i to charBefore] = potentialGears[i to charBefore]?.plus(it.first.toInt()) ?: listOf(it.first.toInt())
            }

            if (charAfter < line.length) {//After
                val isGear = line[charAfter] == '*'
                if (isGear) potentialGears[i to charAfter] = potentialGears[i to charAfter]?.plus(it.first.toInt()) ?: listOf(it.first.toInt())
            }

            if (lineAfter < input.size) {//Below
                val specialChars = gearRegex.findOnLineForPart(input, lineAfter, it)
                specialChars.addPotentialGears(input, lineAfter, it, potentialGears)
            }
        }
    }

    return potentialGears.filter { it.value.size == 2 }.map { it.value.first() * it.value.last() }.sum()
}

fun Regex.findAllNumbersOnLine(line: String) =
    findAll(line).map { it.value to it.range.first.coerceAtLeast(0) }.toList()

fun Regex.findOnLineForPart(lines: List<String>, lineIndex: Int, part: Pair<String, Int>): Sequence<MatchResult> {
    val line = lines[lineIndex]
    return findAll(line.subSequence((part.second - 1).coerceAtLeast(0), (part.second + part.first.length + 1).coerceAtMost(line.length)))
}

fun Sequence<MatchResult>.addPotentialGears(lines: List<String>, lineIndex: Int, part: Part, potentialGears: MutableMap<Coord, PartNums>) {
    forEach { gear ->
        val before = lines[lineIndex].charsBeforeChar(part.second)
        potentialGears[lineIndex to gear.range.first + before] = potentialGears[lineIndex to gear.range.first + before]?.plus(part.first.toInt()) ?: listOf(part.first.toInt())
    }
}

fun String.charsBeforeChar(charIndex: Int) = subSequence(0, (charIndex - 1).coerceAtLeast(0)).length