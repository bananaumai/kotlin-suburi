import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun <T> Flow<T>.throttleByTime(wait: Long): Flow<List<T>> = flow {
    val holder = emptyList<T>().toMutableList()
    var nextTime = 0L

    collect {
        val curTime = System.currentTimeMillis()
        if (curTime < nextTime) {
            holder.add(it)
        } else {
            nextTime = curTime + wait
            emit(holder.toList())
            holder.clear()
        }
    }
}

fun <T> Flow<T>.throttleByCount(limit: Long): Flow<List<T>> = flow {
    val holder = emptyList<T>().toMutableList()
    var count = 0

    collect {
        count++
        if (count < limit) {
            holder.add(it)
        } else {
            count = 0
            emit(holder.toList())
            holder.clear()
        }
    }
}

fun main() = runBlocking {
    val f = flow {
        repeat(100) {
            emit(it)
            delay(10)
        }
    }

    f.throttleByTime(100).onEach {println(it)}.collect {println(it)}
}