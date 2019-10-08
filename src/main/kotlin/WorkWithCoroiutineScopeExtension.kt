import kotlinx.coroutines.*

fun main() = runBlocking {
//    val f = Foo()
//    val job = launch {
//        println("launched")
//        with(f) {
//            println("call foo")
//            foo()
//        }
//    }
//    delay(100)
//    job.cancel()

    val j = Foo().run {
        launch {
            println("call foo")
            foo()
        }
    }
    delay(100)
    j.cancel()
}

class Foo() {
    fun CoroutineScope.foo(): Job = launch(Dispatchers.IO) {
        delay(1000)
        println("inside foo")
    }
}