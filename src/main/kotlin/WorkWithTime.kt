import java.util.Date
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun main() {
    val i = Instant.now()
    println("Instant             : $i")
    println("Instant.epochSecond : ${i.epochSecond}")
    println("Instant.epochMilli  : ${i.toEpochMilli()}")
    println("Instant.nano        : ${i.nano}")
    println("Instant.MIN         : ${Instant.MIN}")
    println("Instant.MIN         : ${Instant.MIN.epochSecond}")
    println("Instant.MAX         : ${Instant.MAX}")
    println("Instant.MAX.epochSecond : ${Instant.MAX.epochSecond}")
    println("Long.MAX_VALUE      : ${Long.MAX_VALUE}")

    val d = Date.from(i)
    println("Date                : $d")
    println("Date.time           : ${d.time}")
    d.toInstant()

    val zd = i.atZone(ZoneId.of("Z"))
    println("ZonedDateTime               : $zd")
    println("ZonedDateTime.toEpochSecond : ${zd.toEpochSecond()}")
    println("ZonedDateTime.nano          : ${zd.nano}")
    zd.toInstant()

    val formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC)
    val zd2 = ZonedDateTime.parse("1970-01-01T00:00:00", formatter)
    println("zd2               : $zd2")
}