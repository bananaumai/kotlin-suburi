import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val job = launch {
        foo(this)
        println("here1")
        bar()
        println("here2")
    }
    job.join()
}

fun foo(scope: CoroutineScope) = scope.launch {
    nums().consumeEach {
        println(it)
    }
}

suspend fun bar() = withContext(Dispatchers.Default) {
    launch {
        nums().consumeEach {
            println(it)
        }
    }
}

fun CoroutineScope.nums() = produce {
    var i = 0
    while (i < 50) {
        send(i)
        i++
    }
}