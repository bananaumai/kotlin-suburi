import java.io.File

fun main() {
    val f = File("/tmp", "hoge.test")
    f.deleteOnExit()
    val contents = "test"
    f.outputStream().use {
        it.write(contents.toByteArray())
    }

    val size = contents.toByteArray().size

    println("orig size -> $size")

    val bytes = ByteArray(size * 2)
    f.inputStream().use {
        it.read(bytes)
    }

    println("read bytes size -> ${bytes.size}")

    println(bytes.joinToString(" ") { "%02X".format(it) })

    println("orig text - $contents")
    println("read bytes to text - ${String(bytes)}")

    val trimmed = bytes.takeWhile { it.toInt() != 0 }.toByteArray()
    println("trimmed read bytes text - ${String(trimmed)}")


    f.inputStream().use {
        val b1 = ByteArray(1)
        val ret1 = it.read(b1)
        println("read b1 : $ret1")
        println("b1 : ${String(b1)}")

        val b2 = ByteArray(2)
        val ret2 = it.read(b2)
        println("read b2 : $ret2")
        println("b2 : ${String(b2)}")

        val b3 = ByteArray(3)
        val ret3 = it.read(b3)
        println("read b3 : $ret3")
        println("b3 : ${String(b3)}")

        val b4 = ByteArray(4)
        val ret4 = it.read(b4)
        println("read b4 : $ret4")
        println("b4 : ${String(b4)}")

        val extra = b3.copyOf(1)
        println("extra : ${String(extra)}")
    }
}
