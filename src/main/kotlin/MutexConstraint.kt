import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

fun main() = runBlocking {
    val j1 = launch {
        MutexConstraint.f()
    }

    val j2 = launch {
        MutexConstraint.sf()
    }

    joinAll(j1, j2)
}

class MutexConstraint {
    companion object {
        private val j1 = Job()
        private val s1 = CoroutineScope(j1 + Dispatchers.Default)
        private val j2 = Job()
        private val s2 = CoroutineScope(j2 + Dispatchers.Default)

        private val m = Mutex()

        fun f() {
            s1.launch {
                m.withLock {
                    println("f() : before delay")
                    delay(1000)
                    println("f() : after delay")
                }
            }
        }

        suspend fun sf() = withContext(s2.coroutineContext) {
            m.withLock {
                println("sf() : before delay")
                delay(1000)
                println("sf() : after delay")
            }
        }
    }
}