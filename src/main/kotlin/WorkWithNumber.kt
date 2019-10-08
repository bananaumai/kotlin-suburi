fun main() {
    println("Long.MAX_VALUE               : ${Long.MAX_VALUE}")
    println("Long.MAX_VALUE(hex)          : %X".format(Long.MAX_VALUE))
    println("Long.MIN_VALUE               : ${Long.MIN_VALUE}")
    println("Long.MIN_VALUE(hex)          : %X".format(Long.MIN_VALUE))
    println("-1L(hex)                     : %X".format(-1L))
    println("-0x0000_0000_0000_0001L      : ${-0x0000_0000_0000_0001L}")
    println("-0x7FFF_FFFF_FFFF_FFFFL      : ${-0x7FFF_FFFF_FFFF_FFFFL}")
    println("-0x7FFF_FFFF_FFFF_FFFFL - 1L : ${-0x7FFF_FFFF_FFFF_FFFFL - 1L}")
    println("-0x7FFF_FFFF_FFFF_FFFFL - 2L : ${-0x7FFF_FFFF_FFFF_FFFFL - 2L}")
    println("0xFFFFFFFF00000000L          : ${-0x10_0000_000L}")
    println("0x7FFF_FFFF_FFFF_FFFFu       : ${0x7FFF_FFFF_FFFF_FFFFu}")
    println("0x8000_0000_0000_0000u       : ${0x8000_0000_0000_0000u}")

    println(0b11_11111111_11111111_11111111_11111111L.toString(16))
}