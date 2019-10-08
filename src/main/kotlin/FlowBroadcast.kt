import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select

fun main() = runBlocking {
    val fn: Flow<Int> = flow {
        var n = 0
        while (true) {
            emit(n)
            n++
            delay(500)
        }
    }

    val fs = flow {
        while (true) {
            emit("foo")
            delay(1000)
        }
    }

    launch {
        val ns = fn.onCompletion {
            println("int flow completed")
        }

        val bcn: BroadcastChannel<Int> = ns.broadcastIn(this).apply {
            invokeOnClose { println("int broad cast channel closed") }
        }

        val ss = fs.onCompletion {
            println("string flow completed")
        }

        val bcs: BroadcastChannel<String> = ss.broadcastIn(this).apply {
            invokeOnClose { println("string broad cast channel closed") }
        }

        val sn1 = bcn.openSubscription()
        val ss1 = bcs.openSubscription()
        val m1 = flow {
            while(true) {
                select<Unit> {
                    sn1.onReceive {
                        emit("m1 - $it")
                    }
                    ss1.onReceive {
                        emit("m1 - $it")
                    }
                }
                if (!isActive) {
                    println("m1 is no more active")
                    break
                }
            }
        }.onCompletion {
            println("m1 completed")
            sn1.cancel()
            ss1.cancel()
        }

        val sn2 = bcn.openSubscription()
        val ss2 = bcs.openSubscription()
        val m2 = flow {
            while (true) {
                select<Unit> {
                    sn2.onReceive {
                        emit("m2 - $it")
                    }
                    ss2.onReceive {
                        emit("m2 - $it")
                    }
                }

                if (!isActive) {
                    println("m2 is no more active")
                    break
                }
            }
        }.onCompletion {
            println("m2 completed")
            sn2.cancel()
            ss2.cancel()
        }

        val j1 = launch {
            m1.take(5).collect {
                println("j1 - $it")
            }
        }

        val j2 = launch {
            m2.take(5).collect {
                println("j2 - $it")
            }
        }

        joinAll(j1, j2)

        bcn.cancel()
        bcs.cancel()
    }.join()
}