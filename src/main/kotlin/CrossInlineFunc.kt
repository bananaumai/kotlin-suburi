import java.lang.AssertionError

inline fun higherOrderFunction(crossinline aLambda: () -> Unit) {
    try {
        normalFunction {
            aLambda() //must mark aLambda as crossinline to use in this context.
        }
    } catch (e: Exception) {
        println("caught error in higherOrderFunction : $e")
    }
}

fun normalFunction(aLambda: () -> Unit) {
    aLambda()
}

fun callingFunction() {
    higherOrderFunction {
        try {
            throw Exception()
            println("print")
        } catch (e: Exception) {
            println("e: $e")
            throw e
        }
    }
}

fun main() {
    callingFunction()
}
