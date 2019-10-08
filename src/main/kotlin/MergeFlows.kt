import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.onReceiveOrNull
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select

fun f1(): Flow<Int> = flow {
    repeat (10) {
        emit(1)
        delay(400)
    }
    println("f1() finished")
}

fun f2(): Flow<Int> = flow {
    repeat(10) {
        emit(2)
        delay(200)
    }
    println("f2() finished")
}

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun main() = runBlocking {
    launch {

        println("-----combine behavior----")
        launch {
            val f1a = f1()
            val f2a = f2()

            val combined = f1a.combineTransform(f2a) { na, nb ->
                emit(na)
                emit(nb)
            }

            combined.collect {
                println("combine - $it")
            }
        }.join()

        println("-----merge behavior----")
        launch {
            val f1b = f1()
            val f2b = f2()

//
//            f1b.onCompletion {
//                println("f1b completed")
//                c1.cancel()
//            }
//            f2b.onCompletion {
//                println("f2b completed")
//                c2.cancel()
//            }

            val merged = flow {
                val scope = CoroutineScope(coroutineContext)

                val c1 = f1b.produceIn(scope)
                val c2 = f2b.produceIn(scope)

                while (true) {
                    select<Unit> {
                        c1.onReceiveOrNull() {
                            if (it != null) {
                                emit(it)
                            } else {
//                                println("cancel c1")
                                c1.cancel()
                            }
                            delay(10)
                        }
                        c2.onReceiveOrNull {
                            if (it != null) {
                                emit(it)
                            } else {
//                                println("cancel c2")
                                c2.cancel()
                            }
                            delay(10)
                        }
                    }
                    if (c1.isClosedForReceive && c2.isClosedForReceive) {
                        println("c1 and c2 is canceled")
                        break
                    }
                }
            }

            merged.collect {
                println("merge - $it")
            }
        }.join()

    }.join()
}