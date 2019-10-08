import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.core.inject

interface Repository {
    fun fetch(id: Int): String
}

class DefaultRepository : Repository {
    init {
        println("init DefaultRepository")
    }

    override fun fetch(id: Int) = "$id"
}

inline fun <reified T> getKoinInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}

val mod = module {
    single { DefaultRepository() as Repository }
}

fun main() {
    startKoin {
        modules(mod)
    }

    println(get(1))

    println(get(2))
}

fun get(id: Int): String {
    val repo = getKoinInstance<Repository>()
    return repo.fetch(id)
}
