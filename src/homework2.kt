import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import kotlin.math.PI
import kotlin.math.pow

// https://dmoj.ca/problem/ccc08j2
fun ccc08j2() {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val playlist = mutableListOf("A", "B", "C", "D", "E")

    while (true) {
        val button = reader.readLine().toInt()
        val count  = reader.readLine().toInt()

        if (button == 4) break

        repeat(count) {
            when (button) {
                1 -> playlist.add(playlist.removeAt(0))
                2 -> playlist.add(0, playlist.removeAt(playlist.size - 1))
                3 -> {
                    val tmp = playlist[0]
                    playlist[0] = playlist[1]
                    playlist[1] = tmp
                }
            }
        }
    }
    writer.write(playlist.joinToString(" "))
    writer.flush()
}


// https://dmoj.ca/problem/ccc00s1
fun ccc00s1 () {
    class User (private var quarters: Int, private var playedCount: Int = 0) {
        fun pay (): User {
            this.quarters--
            this.playedCount++
            return this;
        }
        fun reward(reward: Int) {
            this.quarters += reward
        }
        fun isNotBroke(): Boolean {
            return !this.isBroke();
        }
        private fun isBroke (): Boolean {
            return this.quarters <= 0
        }
        fun getPlayedCount (): Int {
            return this.playedCount;
        }
    }
    class Machine(
        private var playedCount: Int,
        private val reward: Int,
        private val rewardCycle: Int
    ) {
        fun play(user: User) {
            this.increase()
            if (this.isReachedRewardCycle()) {
                this.resetRewardCycle()
                user.reward(this.reward)
            }
        }
        private fun increase () {
            this.playedCount++
        }
        private fun isReachedRewardCycle(): Boolean {
            return this.playedCount % this.rewardCycle == 0
        }
        private fun resetRewardCycle() {
            this.playedCount = 0
        }
    }
    class Casino (private val machines: List<Machine>, private var index: Int = 0) {
        fun play (user: User) {
            while (user.isNotBroke()) this.getMachine().play(user.pay())
        }
        fun getMachine() : Machine {
            this.validateIndex()
            return this.machines[this.index++]
        }
        fun validateIndex() {
            if (this.index >= this.machines.size) {
                this.index = 0
            }
        }
    }
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val user = User(reader.readLine().toInt())
    Casino(listOf(
        Machine(reader.readLine().toInt(), 30, 35),
        Machine(reader.readLine().toInt(), 60, 100),
        Machine(reader.readLine().toInt(), 9, 10)
    )).play(user)
    writer.write("Martha plays ${user.getPlayedCount()} times before going broke.")
    writer.flush()
}

// https://dmoj.ca/problem/coci16c1p1
fun coci16c1p1 () {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val givenMegabytes = reader.readLine().toInt()
    val count = reader.readLine().toInt()
    var remainedMegabytes = 0

    for (p in 0 until count) {
        val spentMegabytes = reader.readLine().toInt()
        remainedMegabytes = givenMegabytes + remainedMegabytes - spentMegabytes
    }

    remainedMegabytes += givenMegabytes

    writer.write("$remainedMegabytes")
    writer.flush()
}

// https://dmoj.ca/problem/ccc18j2
fun ccc18j2 () {
    fun isOccupied (c1: Char, c2: Char) = c1 == 'C' && c2 == 'C'
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val spaces = reader.readLine().toInt()
    val yesterday = reader.readLine()
    val today = reader.readLine()
    var occupied = 0;

    for (i in 0..spaces - 1) {
        if (isOccupied(yesterday[i], today[i])) {
            occupied++
        }
    }
    writer.write("$occupied")
    writer.flush()
}

// https://dmoj.ca/problem/coci06c5p1
fun coci06c5p1 () {
    fun moveA (position: Int) = if (position == 3) position else if (position == 2) position - 1 else position + 1
    fun moveB (position: Int) = if (position == 1) position else if (position == 2) position + 1 else position - 1
    fun moveC (position: Int) = if (position == 2) position else if (position == 1) position + 2 else position - 2
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val moves = reader.readLine()
    var position = 1;
    for (move in moves) {
        position = when (move) {
            'A' -> moveA(position)
            'B' -> moveB(position)
            else -> moveC(position)
        }
    }
    writer.write("$position")
    writer.flush()
}

// https://dmoj.ca/problem/ccc18j1
fun ccc18j1 () {
    fun isSame(first: Int, second: Int) = first == second
    fun isTelemarketerDigit (number: Int) = number >= 8;
    fun isTelemarketer (first: Int, second: Int, third: Int, fourth: Int) =
        isTelemarketerDigit(first) &&
        isSame(second, third) &&
        isTelemarketerDigit(fourth)
    fun get (first: Int, second: Int, third: Int, fourth: Int) = if (isTelemarketer(first, second, third, fourth)) "ignore" else "answer"
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    writer.write(get(
        reader.readLine().toInt(),
        reader.readLine().toInt(),
        reader.readLine().toInt(),
        reader.readLine().toInt(),
    ))
    writer.flush()
}

// https://dmoj.ca/problem/ccc19j1
fun ccc19j1 () {
    fun getScore (reader: BufferedReader): Int {
        var accumulator = 0;
        for (i in 3 downTo 1) {
            accumulator += i * reader.readLine().toInt()
        }
        return accumulator;
    }

    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val aScore = getScore(reader);
    val bScore = getScore(reader);
    writer.write(if (aScore > bScore) "A" else if (bScore > aScore) "B" else "T")
    writer.flush()
}


// https://dmoj.ca/problem/dmopc14c5p1
fun dmopc14c5p1 () {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))
    val radius = reader.readLine().toDouble()
    val height = reader.readLine().toDouble()
    val volume = (PI * radius.pow(2) * height) / 3;
    writer.write(volume.toString())
    writer.flush()
}


