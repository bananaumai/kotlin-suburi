import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

fun main() {
    val f1Name = "/tmp/kotlin-suburi-file-write.txt"
    val f1 = File(f1Name)
    f1.writeText("hello world")

    val f2Name = "/tmp/kotlin-suburi-file-moved.txt"
    val f2 = File(f2Name)
    f2.writeText("HELL A WORLD")

    Files.move(f1.toPath(), f2.toPath()) // throws java.nio.file.FileAlreadyExistsException
}