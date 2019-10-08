import java.nio.ByteBuffer

fun main() {

    data class TestCase(val desc: String, val value: Long)

    val cases = arrayOf(
        TestCase("1L", 1L),
        TestCase("Long.MAX_VALUE", Long.MAX_VALUE),
        TestCase("Long.MIN_VALUE", Long.MIN_VALUE),
        TestCase("-1L", -1L)
    )

    cases.forEach { case ->
        val bytes = ByteBuffer.allocate(Long.SIZE_BYTES).putLong(case.value).array()
        println("${case.desc} is ${bytes.toHexString()}")
    }

    val bytes96 = ByteBuffer.allocate(Int.SIZE_BYTES+Long.SIZE_BYTES).putInt(Int.MAX_VALUE).putLong(Long.MAX_VALUE).array()
    println("bytes96 is ${bytes96.toHexString()}")
}

fun ByteArray.toHexString(): String {
    return this.joinToString(" ") { "%02X".format(it) }
}