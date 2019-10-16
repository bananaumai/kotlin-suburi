import kotlinx.coroutines.*

val job = SupervisorJob()
val scope = CoroutineScope(Dispatchers.Default + job)

fun main() = runBlocking {
    scope.launch {
        println("foo")
        delay(200)
        println("bar")
    }

    delay(100)

    job.cancelChildren()

    scope.launch {
        println("buzz")
        delay(200)
        println("never 1")
    }

    delay(100)

    scope.launch {
        println("hello")
        job.cancel()
        delay(100)
        println("never 2")
    }


    scope.launch {
        println("never 3")
    }.join()

    println("fin")
}
