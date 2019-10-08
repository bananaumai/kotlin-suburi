import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class MyCS(ctx: CoroutineContext = Dispatchers.Default) : CoroutineScope {
    override val coroutineContext: CoroutineContext = ctx
    
}