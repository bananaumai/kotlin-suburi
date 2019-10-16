import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect

fun main() = runBlocking {
    val f = produceFlow()
    val c = produceChan()

    val job = launch {
        launch {
            f.collect {
                println("collect $it")
            }
        }

        launch {
            delay(100)
            c.consumeEach {
                println("consume $it")
            }
        }
    }

    job.invokeOnCompletion {
        println("job was completed - $it")

        try {
            throw Exception("error")
        } catch(e: Exception) {
            println("caught exception $e")
        }
    }

    delay(300)

    job.cancel()

    delay(300)
}

fun produceFlow() = channelFlow {
    launch {
        println("flow 1")
        offer(1)
        delay(100)
        println("flow 2")
        offer(2)
        delay(100)
        println("flow 3")
        offer(3)
    }

    awaitClose {
        throw Exception("exception in awaitClose in produceFlow")
    }
}

fun CoroutineScope.produceChan() = produce {
    launch {
        println("chan 1")
        offer(1)
        delay(100)
        println("chan 2")
        offer(2)
        delay(100)
        println("chan 3")
        offer(3)
    }

    awaitClose {
        throw Exception("exception in awaitClose in produceChan")
        println("Channel was closed")
    }
}
