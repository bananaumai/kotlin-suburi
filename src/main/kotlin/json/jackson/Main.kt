package json.jackson

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.util.*

val mapper = jacksonObjectMapper()

data class D(val name: String, val obj: Map<String, Any>)

fun main() {
    fun deserialize(json: String): D {
        return mapper.readValue(json)
    }

    fun serialize(d: D): String {
        return mapper.writeValueAsString(d)
    }

    val json1 = """{"name": "test", "obj": {"foo": "bar"}}""".trimIndent()
    val d1 = deserialize(json1)
    println(d1)
    println(serialize(d1))

    val d2 = D("test", mapOf("foo" to "bar"))
    val json2 = serialize(d2)
    println(json2)
    println(mapper.readValue<D>(json2))

    val nInt = mapper.readTree("10")
    println("nInt.nodeType: ${nInt.nodeType}")
    println(nInt.intValue())

    val nDouble = mapper.readTree("1.0")
    println(nDouble.nodeType)

    val js = listOf("1", "3000000000", "3000000000000000000000000", "1.0", "\"a\"", "true", """[1,2,3]""", """{"foo":"bar"}""", "null")

    for (j in js) {
        val n = mapper.readTree(j)
        print("$j - nodeType: ${n.nodeType} - ")

        if (n.isBigDecimal) {
            println("[BigDecimal]")
        }
        if (n.isBigInteger) {
            print("[BigInteger]")
        }
        if (n.isBinary) {
            print("[Binary]")
        }
        if (n.isBoolean) {
            print("[Boolean]")
        }
        if (n.isDouble) {
            print("[Double]")
        }
        if (n.isEmpty) {
            print("[Empty]")
        }
        if (n.isFloat) {
            print("[Float]")
        }
        if (n.isFloatingPointNumber) {
            print("[FloatingPointNumber]")
        }
        if (n.isInt) {
            print("[Int]")
        }
        if (n.isIntegralNumber) {
            print("[IntegerNumber]")
        }
        if (n.isLong) {
            print("[Long]")
        }
        if (n.isNull) {
            print("[Null]")
        }
        if (n.isNumber) {
            print("[Number]")
        }
        if (n.isPojo) {
            print("[Pojo]")
        }
        if (n.isShort) {
            print("[Short]")
        }
        if (n.isArray) {
            print("[Array]")
        }
        if (n.isTextual) {
            print("[Textual]")
        }
        if (n.isContainerNode) {
            print("[ContainerNode]")
        }
        if (n.isMissingNode) {
            print("[Missing]")
        }
        if (n.isObject) {
            print("[Object]")
        }
        if (n.isValueNode) {
            print("[ValueNode]")
        }
        println("")
    }

    val os = listOf(1, 10L, 1.0f, 1.0, "ab", true, listOf(1,2,3), mapOf("foo" to "bar"), null, D("a", mapOf("foo" to "bar")))
    for (o in os) {
        println(mapper.writeValueAsString(o))
    }

    val map = mapper.readValue<Map<String, Any>>("""{"foo": "bar", "int": 1, "long": 3000000000}""")
    println("foo: ${map["foo"]}(${map["foo"]?.javaClass}), int: ${map["int"]}(${map["int"]?.javaClass}), long:${map["long"]}(${map["long"]?.javaClass})")

    val foo: String = map["foo"] as String
    val int: Int = map["int"] as Int
    val long: Long = map["long"] as Long

    println("$foo, $int, $long")


    val anyObj = mapper.readValue<Any>("""{"foo": "bar", "arr": [1, 2, 3]}""")
    println(anyObj)

    val anyInt = mapper.readValue<Any>("1")
    println(anyInt)
}
