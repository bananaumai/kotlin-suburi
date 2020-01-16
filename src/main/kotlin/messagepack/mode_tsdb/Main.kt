package messagepack.mode_tsdb

import org.msgpack.core.MessageBufferPacker
import org.msgpack.core.MessagePack
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val timestamp = dateFormat.parse("2020-01-01T00:00:00.000Z").time
    val seriesIDs = listOf("series1", "series2")
    val values = listOf(1.0, 1.0)

    val packer = MessagePack.newDefaultBufferPacker()
    packer.packString("DATA")
    packer.packString("1.0")
    packer.packLong(seriesIDs.size.toLong())
    for (id in seriesIDs) {
        packer.packString(id)
    }
    packer.packLong(timestamp * 1_000_000)
    for (v in values) {
        packer.packDouble(v)
    }

    println(packer.toByteArray().joinToString(" ") { "%02X".format(it) })
}
