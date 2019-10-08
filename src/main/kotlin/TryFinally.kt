fun main() {
    println(tryFinally())
}

fun tryFinally():Int {
    try {
        return 1
    } finally {
        println("finally")
    }
}