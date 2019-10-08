import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.onReceiveOrNull
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun main() = runBlocking {
    val c1 = produce {
        repeat(10) {
            send(1)
            delay(100)
        }
    }

    val c2 = produce {
        repeat(10) {
            send(2)
            delay(200)
        }
    }

    while (true) {
        select<Unit> {
            runCatching {
                c1.onReceive {
                    println(it)
                }
                c2.onReceive {
                    println(it)
                }
            }.fold(
                onSuccess = {},
                onFailure = {e ->
                    if (e is ClosedReceiveChannelException) {
                        println("either c1 or c2 is closed: c1 => ${c1.isClosedForReceive}, c2 => ${c2.isClosedForReceive}")
                    } else {
                        throw e
                    }
                }
            )
        }
        if (c1.isClosedForReceive && c2.isClosedForReceive) {
            break
        }
    }

    println("finish")
}