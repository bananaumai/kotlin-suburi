import kotlinx.coroutines.*

fun main() = runBlocking {
    val d1 = async {
        println("inside d1 - before delay")
        delay(100)
        println("inside d1 - after delay")
        1
    }
    val d2 = async {
        println("inside d2 - before delay")
        delay(100)
        println("inside d2 - after delay")
        2
    }
    delay(200)
    println("outside")
    println("calc: d1 + d2 = ${d1.await() + d2.await()}")
}