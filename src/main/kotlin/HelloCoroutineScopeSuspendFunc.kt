import kotlinx.coroutines.*

fun main() = runBlocking {
    launch(CoroutineName("launch")) {
        println("$coroutineContext")
        println(delayedPlus1(1, 2))
        println(delayedPlus2(1, 2))
        println(aHello().await())
    }
    println("$coroutineContext")
}


suspend fun delayedPlus1(a: Int, b: Int) = coroutineScope {
    println(coroutineContext)
    val d1 = async { delay(100); a }
    val d2 = async { delay(100); b }
    d1.await() + d2.await()
}

suspend fun delayedPlus2(a: Int, b: Int) = withContext(Dispatchers.Default) {
    println(coroutineContext)
    val d1 = async { delay(100); a }
    val d2 = async { delay(100); b }
    d1.await() + d2.await()
}

fun CoroutineScope.aHello() = async {
    println(coroutineContext)
    delay(100)
    "hello"
}