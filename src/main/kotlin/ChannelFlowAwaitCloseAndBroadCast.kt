import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    val f = channelFlow {
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

    val b = f.broadcastIn(this).apply {
        /**
         * If uncomment the following code, Exception below will be raised.
         *
         * ```
         * java.lang.IllegalStateException: Another handler was already registered
         * ```
         *
         * You cannot onClose callback twice for same SendChannel.
         *
         * There is already onClose callback in this code: awaitClose callback inside channelFlow builder
         *
         */
        // invokeOnClose { println("b1 closed") }
    }

    launch {
        b.openSubscription().consumeAsFlow().take(5).collect {
            println(it)
        }
        b.cancel()
    }.join()
}