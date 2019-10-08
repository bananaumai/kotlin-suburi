import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

interface I {
    fun i()
}

class C1 : I {
    override fun i() {
        println("i - c1")
    }

    fun c1() {
        println("original c1")
    }
}

class C2 : I {
    override fun i() {
        println("i - c2")
    }

    fun c2() {
        println("original c2")
    }
}

fun handleIList(l: List<I>) {
    for (i in l) {
        i.i()
    }
}

fun CoroutineScope.handleIChannel(c: Channel<I>) = launch {
    c.consumeEach { i -> i.i() }
}

fun CoroutineScope.emitC1(c: Channel<I>) = launch {
    while (true) {
        if (c.isClosedForSend) {
            break
        }
        c.send(C1())
    }
}

fun main() = runBlocking {
    val c1s = listOf(C1(), C1())
    handleIList(c1s)  // no need to cast

    val c2Ch = Channel<C2>()
    handleIChannel(c2Ch as Channel<I>)  // need to cast
    launch {
        c2Ch.send(C2())
        c2Ch.send(C1()) // can be send C1!
        c2Ch.cancel()
    }.join()

    val c1Ch = Channel<C1>()
    emitC1(c1Ch as Channel<I>)
    launch {
        c1Ch.receive().c1()
        // c1Ch.receive().c2() // impossible
        c1Ch.cancel()
    }.join()
}