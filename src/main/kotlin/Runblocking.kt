import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    bRun()

    runBlocking {
        sRun()
    }
}

fun bRun() = runBlocking {
    launch {
        repeat(3) {
            delay(100)
            println("bRun - $it")
        }
    }

    launch {
        delay(500)
        println("500msec wait")
    }

    println("hello bRun")
}

suspend fun sRun() = coroutineScope {
    launch {
        repeat(10) {
            delay(100)
            println("sRun - $it")
        }
    }

    println("hello sRun")
}
