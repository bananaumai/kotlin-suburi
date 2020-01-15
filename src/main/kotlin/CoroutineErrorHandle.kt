import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext


fun main() = runBlocking {
    val handle1 = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("handle1 : $coroutineContext : $throwable")
    }

    GlobalScope.launch(handle1 + CoroutineName("parent")) {
        val handle2 = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("this will never be printed")
        }

        val c1 = launch(handle2 + CoroutineName("child1")) {
            heavy(1000)
        }

        launch(handle2 + CoroutineName("child2") ) {
            heavy(500)
            throw ArithmeticException("c2 crash")
        }

        launch(handle2 + CoroutineName("child3")) {
            heavy(2000)
        }

        delay(100)

        c1.cancel()
    }.join()

    println("end")
}

suspend fun heavy(wait: Long) {
    println("$coroutineContext start")
    delay(wait)
    println("$coroutineContext finish")
}
