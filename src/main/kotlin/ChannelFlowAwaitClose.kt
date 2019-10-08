import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.broadcastIn
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val f1 = channelFlow {
        launch {
            while (true) {
                offer(1)
                delay(500)
            }
        }

        awaitClose {
            println("f1 closed")
        }
    }

    val f2 = channelFlow {
        launch {
            while (true) {
                offer(2)
                delay(500)
            }
        }

        awaitClose {
            println("f2 closed")
        }
    }

    val j1 = launch {
        f1.take(5).collect {
            println(it)
        }
    }

    val j2 = launch {
        f2.take(5).collect {
            println(it)
        }
    }

    joinAll(j1, j2)


}