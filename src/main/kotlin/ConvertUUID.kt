import java.nio.ByteBuffer
import java.util.*

fun main() {
    val uuid = UUID.randomUUID()
    println(uuid)
    val bytes = ByteBuffer.allocate(16).putLong(uuid.mostSignificantBits).putLong(uuid.leastSignificantBits).array()
    println(bytes.joinToString(" ") { "%02X".format(it) })

    val buf = ByteBuffer.wrap(bytes)
    val uuid2 = UUID(buf.long, buf.long)
    println(uuid2)
}