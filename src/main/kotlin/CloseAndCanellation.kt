import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.Closeable

fun main() = runBlocking {
    val c = C()

    val j = launch {
        c.use {
            c.doSomething()
        }
    }

    delay(100)

    j.cancel()
}

class C : AutoCloseable {
    override fun close() {
        println("closed")
    }

    suspend fun doSomething() {
        println("start")
        delay(1000)
        println("finish")
    }
}
