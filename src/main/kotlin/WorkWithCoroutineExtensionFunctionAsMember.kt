import kotlinx.coroutines.*

fun main() = runBlocking {
    val j = launch {
        println("main")
        Bar().run { bar() }
    }
    delay(100)
    j.cancel()
}

class Bar {
    private val j = Job()
    private val s = CoroutineScope(j)

    fun CoroutineScope.bar() = launch(s.coroutineContext) {
        delay(1000)
        println("foo")
    }
}