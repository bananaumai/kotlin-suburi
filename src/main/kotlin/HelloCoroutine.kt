import kotlin.concurrent.thread

fun main() {
    thread {
        println("world")
    }
    println("hello")
}