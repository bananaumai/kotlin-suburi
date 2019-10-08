import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

fun main() = runBlocking {
    val j1 = launch {
        println(mPlusTwo(1))
    }

    val j2 = launch {
        println(mPlusTwo(2))
    }

    joinAll(j1, j2)

    val j3 = launch {
        println(plusTwo(1))
    }

    val j4 = launch {
        println(plusTwo(2))
    }

    joinAll(j3, j4)
}

val mutex = Mutex()

suspend fun mPlusTwo(n: Int): Int = coroutineScope {
    val tmp = mPlusOne(n)
    mutex.withLock {
        println("inside mutex lock : $n")

        delay(100)
        //mPlusOne(mPlusOne(n))
        //1 + mPlusOne(n)

        println("inside mutex lock after delay : $n")

        1 + tmp
    }
}

suspend fun mPlusOne(n: Int): Int = coroutineScope {
    mutex.withLock {
        delay(100)
        n + 1
    }
}

suspend fun plusTwo(n: Int): Int {
    println("before delay $n")
    delay(100)
    println("after delay $n")
    return n + 2
}