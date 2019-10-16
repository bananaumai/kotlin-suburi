import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

fun main() = runBlocking {
    val supervisor = SupervisorJob()
    val supervisorScope = CoroutineScope(supervisor)

    val p = produce {
        send(1)
        delay(100)
        send(2)
        delay(100)
        send(3)
    }

    supervisorScope.launch {
        launch {
            p.consumeEach {
                println("consume $it")
                try {
                    throw Exception("test")
                } catch(e: Exception) {
                    println("catch")
                    throw e
                }

            }
        }
        println("first launch - 1")
        delay(500)
        println("first launch - 2")
    }

    supervisorScope.launch {
        println("second launch - 1")
        delay(100)
        println("second launch - 2")
    }

    delay(1000)
}