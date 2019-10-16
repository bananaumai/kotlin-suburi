import kotlinx.coroutines.*
import kotlinx.coroutines.channels.onReceiveOrNull
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

@InternalCoroutinesApi
fun main() = runBlocking {
    val c1 = produce {
        repeat(4) { send(1); delay(100) }
    }

    val c2 = produce {
        repeat(4) { send(2); delay(200) }
    }

    launch {
        while (true) {
            select<Unit> {
                c1.onReceiveOrClosed {
                    if (!it.isClosed) {
                        println(it.valueOrNull)
                    }
                }
                c2.onReceiveOrClosed {
                    if (!it.isClosed) {
                        println(it.valueOrNull)
                    } else {
                        println("c1 is closed")
                        delay(1000)
                    }
                }
            }
            if (c1.isEmpty || c2.isEmpty) {
                break
            }
        }
    }.join()

    println("finish")
}