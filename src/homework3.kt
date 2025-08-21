import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.util.Locale
import kotlin.collections.plusAssign

// https://dmoj.ca/problem/coci17c2p2
fun coci17c2p2() {
    fun calculate(reader: BufferedReader): List<String> {
        val (n, m) = reader.readLine().split(" ").map { it.toInt() }
        val words = mutableListOf<String>().apply { repeat(n) { add(reader.readLine()) } }
        val letters = mutableListOf<Char>().apply { repeat(m) { add(reader.readLine()[0]) } }
        val usageCount = mutableMapOf<String, Int>().apply { words.forEach { put(it, 0) } }
        val grouped = words.groupBy { it[0] }
        val result = mutableListOf<String>()

        letters.forEach { letter ->
            val candidates = grouped[letter] ?: emptyList()
            val selected = candidates.minWith(compareBy({ usageCount[it] ?: 0 }, { it }))!!
            usageCount.set(selected, usageCount.get(selected)!! + 1)
            result.add(selected)
        }
        return result
    }

    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    calculate(reader).forEach { writer.write("$it\n") }
    writer.flush()
}


// https://dmoj.ca/problem/crci06p1
fun crci06p1() {
    fun calculate(reader: BufferedReader): List<Int> {
        val villagersCount = reader.readLine().toInt()
        val eveningCount = reader.readLine().toInt()
        val villagers = mutableMapOf<Int, MutableSet<Int>>()
            .apply { repeat(villagersCount) { put(it + 1, mutableSetOf()) } }
        var currentSong = 1

        repeat(eveningCount) {
            val attendees = reader.readLine().split(" ").map { it.toInt() }.drop(1)

            if (attendees.contains(1)) {
                attendees.forEach { villagers[it]!!.add(currentSong) }
                currentSong++
            } else {
                val union = attendees
                    .flatMap { villagers[it]!! }
                    .toSet()
                attendees.forEach { villagers[it]!!.apply { clear(); addAll(union) } }
            }
        }
        val allSongs = (1 until currentSong).toSet()
        return villagers
            .filter { it.value.containsAll(allSongs) }
            .map { it.key }
            .sorted()
    }

    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    calculate(reader).forEach { writer.write("$it\n") }
    writer.flush()
}

// https://dmoj.ca/problem/coci15c2p1
fun coci15c2p1 () {
    val keypad = mapOf(
        2 to "abc",
        3 to "def",
        4 to "ghi",
        5 to "jkl",
        6 to "mno",
        7 to "pqrs",
        8 to "tuv",
        9 to "wxyz"
    )
    fun calculate (reader: BufferedReader): Int {
        var count = 0
        val wordCount = reader.readLine().trim().toInt()
        val words = mutableListOf<String>()
            .apply { repeat(wordCount) { add(reader.readLine().trim()) } }
        val pressed = reader.readLine()
            .trim()
            .filter { it in '2'..'9' }
            .map { it.digitToInt() }

        words
            .filter { it.length == pressed.size }
            .forEach { word ->
                if (word.withIndex().all { (i, char) ->
                    keypad.get(pressed.get(i))!!.contains(char)
                }) {
                    count++
                }
        }
        return count
    }
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val outputs = mutableListOf<Int>()
    while (true) {
        if (!reader.ready()) break
        outputs += calculate(reader)
    }
    writer.write(outputs.joinToString("\n"))
    writer.flush()
}

// https://dmoj.ca/problem/cco99p2
fun cco99p2 () {
    fun ordinal(n: Int): String {
        return when {
            n % 100 in 11..13 -> "${n}th"
            n % 10 == 1 -> "${n}st"
            n % 10 == 2 -> "${n}nd"
            n % 10 == 3 -> "${n}rd"
            else -> "${n}th"
        }
    }

    fun calculate (reader: BufferedReader): String {
        val map = mutableMapOf<String, Int>()
        val (wordCount, commonCount) = reader.readLine().split(" ").map { it.toInt() }
        mutableListOf<String>()
            .apply { repeat(wordCount) { add(reader.readLine()) } }
            .forEach {
                if (map.containsKey(it)) {
                    map[it] = map[it]!! + 1
                } else {
                    map[it] = 1
                }
            }
        val target = map.entries
            .filter { entry ->
                map.values.count { it > entry.value } == commonCount - 1
            }
            .map { it.key }
            .sorted()

        return buildString {
            append("${ordinal(commonCount)} most common word(s):\n")
            target.forEach { append(it).append("\n") }
            append("\n")
        }
    }
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val outputs = mutableListOf<String>()
    val datasetCount = reader.readLine().toInt()
    repeat(datasetCount) {
        outputs += calculate(reader)
    }
    writer.write(outputs.joinToString("\n"))
    writer.flush()
}

// https://dmoj.ca/problem/ecoo19r2p1
fun ecoo19r2p1 () {
    fun calculate (reader: BufferedReader): Int {
        val set = mutableSetOf<String>()
        val addressCount = reader.readLine().toInt()
        mutableListOf<String>()
            .apply { repeat(addressCount) { add(reader.readLine().toString()) } }
            .forEach {
                val (identifier, domain) = it.lowercase().split("@")
                val local = identifier.replace(".", "").split("+").get(0)
                val email = "$local@$domain"
                set.add(email)
            }
        return set.size
    }
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val outputs = mutableListOf<Int>()
    while (true) {
        if (!reader.ready()) break
        outputs += calculate(reader)
    }
    writer.write(outputs.joinToString("\n"))
    writer.flush()
}

// https://dmoj.ca/problem/ecoo17r3p1
fun ecoo17r3p1 () {
    fun calculate (reader: BufferedReader): Int {
        val dateCount = reader.readLine().toString().split(" ").map { it.toInt() }.get(1)
        var dozen = 0;
        mutableListOf<List<Int>>()
            .apply {
                repeat(dateCount) {
                    val salesCountsOfADay = reader.readLine().toString().split(" ").map { it.toInt() }
                    val totalSalesCount = salesCountsOfADay.sum()

                    if (totalSalesCount % 13 == 0) {
                        dozen += totalSalesCount / 13
                    }
                    add(salesCountsOfADay)
                }
            }
            .let {
                it[0].indices.forEach { i ->
                    var sum = 0
                    it.forEach { row ->
                        sum += row[i]
                    }
                    if (sum % 13 == 0) {
                        dozen += sum / 13
                    }
                }
            }
        return dozen
    }
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val outputs = mutableListOf<Int>()
    while (true) {
        if (!reader.ready()) break
        outputs += calculate(reader)
    }
    writer.write(outputs.joinToString("\n"))
    writer.flush()
}


// https://dmoj.ca/problem/ecoo17r1p1
fun ecoo17r1p1 () {
    fun calculate (reader: BufferedReader): String {
        val cost = reader.readLine().toInt()
        val percentages = reader.readLine().toString().split(" ").map { it.toDouble() }
        val studentsCount = reader.readLine().toInt()
        val costs = listOf<Int>(12, 10, 7, 5)
        val proceeds = percentages
            .map { percent -> (studentsCount * percent).toInt() }
            .toMutableList()
            .also {
                it[it.withIndex().maxByOrNull { it.value }!!.index] += studentsCount - it.sum();
            }
            .mapIndexed { index, student -> student * costs[index] }
            .sum()
            .toDouble() / 2.0
        return if (proceeds < cost) "YES" else "NO"
    }
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val outputs = mutableListOf<String>()
    while (true) {
        if (!reader.ready()) break
        outputs += calculate(reader)
    }
    writer.write(outputs.joinToString("\n"))
    writer.flush()
}


// https://dmoj.ca/problem/ccc18s1
fun ccc18s1 () {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    writer.write(
        mutableListOf<Int>()
        .apply { repeat(reader.readLine().toInt()) { add(reader.readLine().toInt()) } }
        .also { it.sort() }
        .let {
            it.mapIndexed { i, village ->
                if (i != 0 && i != it.size - 1) (it[i + 1].toDouble() - it[i - 1].toDouble()) / 2.0
                else Double.MAX_VALUE
            }
        }
        .minOrNull()
        .let { String.format(Locale.US, "%.1f", it!!) }
    )
    writer.flush()
}


