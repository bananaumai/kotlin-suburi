interface Inf {
    fun f(n: Int? = null): Int
}

class InfImpl : Inf {
    override fun f(n: Int?): Int {
        val base = 2
        return if (n == null) {
            base
        } else {
            base * n
        }
    }
}

fun main() {
    printInf(InfImpl())
}

fun printInf(i: Inf) {
    println(i.f())
}