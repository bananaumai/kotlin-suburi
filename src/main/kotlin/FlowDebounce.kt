import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

fun main() = runBlocking {
    val limit = 10

    launch {
        emitNumber().debounce(100).collectIndexed { i, n ->
            if (i >= limit) {
                cancel()
            }

            println("collect $n")
        }
    }.join()
}

fun emitNumber() = flow<Int> {
    var n = 0
    while (true) {
        println(n)
        emit(n)
        n++
        delay(100)
    }
}