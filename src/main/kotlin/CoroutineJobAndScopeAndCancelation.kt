import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = SupervisorJob()
    val scope1 = CoroutineScope(job)
    val scope2 = CoroutineScope(job)

    scope2.launch {
        launch {
            repeatedPrint("a", 5)
        }
    }.invokeOnCompletion {
        println("completed a - job(${job}) - scope(${scope1}) - $it")
    }

    val childJob0 = scope1.launch {
        launch {
            repeatedPrint("0", 5)
        }
    }

    childJob0.invokeOnCompletion {
        println("completed 0 - job(${job}) - scope(${scope1}) - $it")
    }

    val childJob1 = scope1.launch {
        launch {
            repeatedPrint("1", 5)
        }

        //cancel() // equivalent with coroutineContext.cancel()
        //coroutineContext.cancel()
        //coroutineContext.cancelChildren()
        scope1.cancel()
        //job.cancel()

        delay(100)

        println("1 --- here")
    }

    childJob1.invokeOnCompletion {
        println("completed 1 - job(${job}) - scope(${scope1}) - $it")
    }

    childJob1.join()

    val childJob2 = scope1.launch {
        launch {
            repeatedPrint("2", 5)
        }
    }

    childJob2.invokeOnCompletion {
        println("completed 2 - job(${job}) - scope(${scope1}) - $it")
    }

    delay(1000)
}

suspend fun repeatedPrint(str: String, count: Int) {
    repeat(count) {
        println(str)
        delay(100)
    }
}