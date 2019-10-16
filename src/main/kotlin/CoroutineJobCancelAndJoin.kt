import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() = runBlocking {
    val job = launch {
        repeat(10) {
            println(it)
            delay(100)
        }
    }

    job.invokeOnCompletion { e ->
        println("completed : $e")
    }

    delay(500)

    var j: Job? = job

    if (Random.nextBoolean()) {
        j!!.cancel()
        println("canceled job")
        j = null
    }

    if (j != null && j.isActive) {
        j.join()
        println("job is joined")
    } else {
        println("job is no more active")
    }

    println("fin")

}