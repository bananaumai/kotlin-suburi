import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random.Default.nextBoolean

suspend fun s1() {
    try {
        println("s1 begin")
        delay(1000)
        println("s1 end")
    } catch(e: Exception) {
        println("s1 error : $e")
        throw e
    }
}

suspend fun s2() {
    try {
        println("s2 begin")
        delay(1000)
        println("s2 end")
    } catch(e: Exception) {
        println("s2 error : $e")
        throw MyException(e)
    }
}

class MyException(cause : Throwable) : RuntimeException(cause)

fun main() = runBlocking {
    val j = launch {
        if (nextBoolean()) { s1() } else { s2() }
    }
    delay(100)
    j.cancel()
    j.join()
    println("fin")
}
