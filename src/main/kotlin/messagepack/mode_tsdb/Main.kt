package messagepack.mode_tsdb

import org.msgpack.core.MessageBufferPacker
import org.msgpack.core.MessagePack
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

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

    var bytes = packer.toByteArray()
    println("${packer.bufferSize}")
    packer.clear()
    println("${packer.bufferSize}")

    for (id in seriesIDs) {
        packer.packString(id)
    }
    packer.packLong(timestamp * 1_000_000)
    for (v in values) {
        packer.packDouble(v)
    }
    bytes += packer.toByteArray()

    println("${packer.bufferSize}")
    packer.clear()
    println("${packer.bufferSize}")

    packer.close()

    //val bytes = packer.toByteArray()
    val expected = "A4 44 41 54 41 A3 31 2E 30 02 A7 73 65 72 69 65 73 31 A7 73 65 72 69 65 73 32 CF 15 E5 9A 35 B9 8A 00 00 CB 3F F0 00 00 00 00 00 00 CB 3F F0 00 00 00 00 00 00"
    val actual = bytes.joinToString(" ") { "%02X".format(it) }
    if (expected != actual) {
        println("failure")
        println("expected: $expected")
        println("actual  : $actual")
        exitProcess(1)
    } else {
        println("success: $actual (${bytes.size})")
    }
}
