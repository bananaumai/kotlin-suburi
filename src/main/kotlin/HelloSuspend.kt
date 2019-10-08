import kotlinx.coroutines.*

fun main() = runBlocking {
    normalHello()
    delayedHello()
}

suspend fun delayedHello() {
    delay(100)
    println("delayed hello")
}

// suspend keyword here is redundant because no suspend function is used inside
suspend fun normalHello() {
    println("normal hello")
}