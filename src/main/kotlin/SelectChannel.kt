import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.onReceiveOrNull as onReceiveOrNullExt
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

@ExperimentalCoroutinesApi
fun main() = runBlocking {
    val c1: ReceiveChannel<Int> = produce {
        repeat(4) { send(1); delay(100) }
    }

    val c2: ReceiveChannel<Int> = produce {
        repeat(4) { send(2); delay(200) }
    }

    launch {
        while (true) {
            select<Unit> {
                c1.onReceiveOrNull {
                    if (it != null) {
                        println(it)
                    }
                }
                c2.onReceiveOrNull {
                    if (it != null) {
                        println(it)
                    }
                }
            }
            if (c1.isClosedForReceive && c2.isClosedForReceive) {
                break
            }
        }
    }.join()

    println("finish")
}