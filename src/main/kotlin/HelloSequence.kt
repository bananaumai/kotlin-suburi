import kotlinx.coroutines.*

fun main() = nat().filter{ it % 2 == 0 }.take(10).forEach { println(it) }

fun nat() = sequence {
    var nat = 0
    while (true) {
        yield(nat)
        nat++
    }
}