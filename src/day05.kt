fun main() {
    val input = getInputString("day05")
    println("Part 1 answer: ${part1(input)}")//31599214
    println("Part 2 answer: ${part2(input)}")//20358599 - 1m47s
}

private fun part1(input: String): Long {
    val (seeds, maps) = getSeedsAndMaps(input)
    return seeds.map { seed ->
        var matching = seed
        maps.forEach {
            matching = processSeed(matching, it)
        }
        matching
    }.min()
}

private fun part2(input: String): Long {
    val (seeds, maps) = getSeedsAndMaps(input)

    val allSeeds = seeds.windowed(2,2) {
        (it.first()..<it.first()+it[1])
    }

    val reveresedMaps = maps.reversed()

    val highestLocation = reveresedMaps.first().second.map {
        it.first+it.third
    }.max()

    val locations = (0..<highestLocation)

    locations.forEachIndexed { i, location ->
        println("$i of ${locations.last+1}")
        var matching = location
        reveresedMaps.forEach { map ->
            for (range in map.second) {
                val (destinationStart, sourceStart, length) = range
                if ((destinationStart..<destinationStart + length).contains(matching)) {
                    matching = sourceStart + (matching - destinationStart)
                    break
                }
            }
        }

        allSeeds.forEach { if (it.contains(matching)) return@part2 location }
    }
    return -1
}

fun processSeed(seed: Long, map: Pair<String, List<Triple<Long, Long, Long>>>): Long {
    var matching = seed
    for (range in map.second) {
        val (destinationStart, sourceStart, length) = range
        if ((sourceStart..<sourceStart + length).contains(matching)) {
            matching = destinationStart + (matching - sourceStart)
            break
        }
    }
    return matching
}

fun getSeedsAndMaps(input: String): Pair<List<Long>, List<Pair<String, List<Triple<Long, Long, Long>>>>> {
    val seeds = input.substringBefore("\n\n").split("seeds: ")[1].split(" ").map { it.toLong() }
    val mapNames = Regex(".*-to-.*").findAll(input.substringAfter("\n"))
    val maps = mapNames.map {
        val rawRanges = input.substringAfter(it.value).trim().substringBefore("\n\n").split("\n")
        val ranges = rawRanges.map {
            val (destinationStart, sourceStart, length) = it.split(" ").map { it.toLong() }
            Triple(destinationStart, sourceStart, length)
        }
        it.value to ranges
    }.toList()
    return seeds to maps
}