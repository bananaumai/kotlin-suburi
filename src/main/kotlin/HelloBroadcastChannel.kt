import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    launch {
        val bc: BroadcastChannel<Int> = BroadcastChannel(1)

        launch {
            val sub = bc.openSubscription()

            delay(100)

            repeat(5) {
                println("sub1: ${sub.receiveOrNull()}")
            }

            sub.cancel()
        }

        launch {
            val sub = bc.openSubscription()

            repeat(5) {
                println("sub2: ${sub.receiveOrNull()}")
            }

            sub.cancel()
        }

        launch {
            repeat(20) {
                println("send: $it")
                bc.send(it)
            }
        }.join()
    }.join()
}