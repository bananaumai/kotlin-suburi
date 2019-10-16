import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val chan = Channel<Int>()

    launch {
        chan.consumeEach {
            println(it)
        }
    }

    launch {
        chan.offer(1)
        delay(100)
        chan.offer(2)
        delay(100)
        chan.offer(3)
    }

    delay(200)
    chan.close()

    delay(100)
}
