import kotlinx.coroutines.*

fun main() = runBlocking() {
    test()
}

//suspend fun test() = coroutineScope {
suspend fun test() = supervisorScope {
    println("----------------------------------------------------------------------------------------")

    launch {
        try {
            throw Exception("launch failed")
            println("never print this line")
        } catch (e: Exception) {
            println("catch exception inside launch builder : $e")
        }
    }.join()

    println("----------------------------------------------------------------------------------------")

    // Exception thrown from launch builder will automatically handled
    launch {
        throw Exception("launch failed")
        println("never print this line")
    }.join()

    println("----------------------------------------------------------------------------------------")

    // Exception thrown from launch builder can not be caught directly
    try {
        launch {
            throw Exception("launch failed")
            println("never print this line")
        }.join()
    } catch (e: Exception) {
        println("never print this line")
    }

    println("----------------------------------------------------------------------------------------")

    val s = async {
        try {
            throw Exception("deffered job failed")
            "hey"
        } catch (e: Exception) {
            println("catch error inside async builder : $e") // will be executed
            "yo"
        }
    }.await()
    println(s) // will be executed

    println("----------------------------------------------------------------------------------------")

    // Exception thrown from async can be caught outside and must be caught
    try {
        val s = async {
            throw Exception("deffered job failed")
            "hey"
        }.await()
        println(s) // will not be executed
    } catch (e: Exception) {
        println("catch error outside async builder : $e")
    }
    println("yo")

    println("----------------------------------------------------------------------------------------")

    println("fin")

    println("----------------------------------------------------------------------------------------")
}
