import java.time.Instant

fun main() {
    println("=========Base knowledge(Number)===========")

    println("Long.MAX_VALUE is       : ${Long.MAX_VALUE}")
    println("Instant.MAX.epochSecond : ${Instant.MAX.epochSecond}")
    println("Int.MAX_VALUE is        : ${UInt.MAX_VALUE}")
    println("Int.MIN_VALUE is        : ${Int.MIN_VALUE}")
    println("Instant.MAX.epochSecond : ${Instant.MIN.epochSecond}")
    println("Long.MIN_VALUE is       : ${Long.MIN_VALUE}")
    println("34bit(Long) is          : ${0b11_11111111_11111111_11111111_11111111L}")
    println("32bit(Long) is          : ${0b11111111_11111111_11111111_11111111L}")


    println("=========Test data========================")

    // Epoch second of May 07, 1982 7:07:07 AM GMT
    val es1 = 389603227L
    println("es1 is May 07, 1982 7:07:07 AM GMT : $es1")

    // Epoch second of Sep 13, 2019 7:07:07 AM GMT
    val es2 = 1568358427L
    println("es2 is Sep 13, 2019 7:07:07 AM GMT : $es2")

    // Epoch second of Instant.MAX.epochSecond
    val max = Instant.MAX.epochSecond
    println("max is Instant.MAX.epochSecond     : $max")

    println("es1 hex is     : ${es1.toString(radix = 16)}(${es1.toString(radix = 16).length})")
    println("es2 hex is     : ${es2.toString(radix = 16)}(${es2.toString(radix = 16).length})")
    println("max hex is     : ${max.toString(radix = 16)}(${max.toString(radix = 16).length})")

    println("es1 binary is     : ${es1.toString(radix = 2)}(${es1.toString(radix = 2).length})")
    println("es2 binary is     : ${es2.toString(radix = 2)}(${es2.toString(radix = 2).length})")
    println("max binary is     : ${max.toString(radix = 2)}(${max.toString(radix = 2).length})")


    println("=========Test epochSecond(Long) 34 bit right shift===========")

    val es1ushr = es1 ushr 34
    val es2ushr = es2 ushr 34
    val maxushr = max ushr 34

    println("(es1 ushr 34) is  : $es1ushr(${es1ushr.toString(radix = 2)})")
    println("(es2 ushr 34) is  : $es2ushr(${es2ushr.toString(radix = 2)})")
    println("(max ushr 34) is  : $maxushr(${maxushr.toString(radix = 2)})")

    println("=========Test 34 nanosec(Int) 34 bit left shift===========")

    // In timestamp 64 and timestamp 96 formats, nanoseconds must not be larger than 999999999.
    val nMax = 999_999_999
    println("nMax is nanosecond max number : $nMax")
    println("nMax hex is : ${nMax.toString(radix = 16)}(${nMax.toString(radix = 16).length})")
    println("nMax binary is : ${nMax.toString(radix = 2)}(${nMax.toString(radix = 2).length})")

    val nMaxshl = nMax.toLong() shl 34
    println("(nMax.toLong shl 34) is : $nMaxshl(%X)".format(nMaxshl))

    val iMaxshl = Int.MAX_VALUE.toLong() shl 34
    println("(Int.MAX_VALUE shl 34) is : $iMaxshl(%X)".format(iMaxshl))

    val i1shl = 1L shl 34
    println("(1 shl 34) is : $i1shl(%X)".format(i1shl))

}