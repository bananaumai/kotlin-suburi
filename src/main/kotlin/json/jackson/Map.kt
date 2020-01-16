package json.jackson

import com.fasterxml.jackson.module.kotlin.SequenceSerializer.serialize
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.util.*

val mapper = jacksonObjectMapper()

fun main() {
    fun parseJson(json: String): Map<String, Any> {
        return mapper.readValue(json)
    }

    fun serialize(map: Map<String, Any>): String {
        return mapper.writeValueAsString(map)
    }

    val json1 = """
        {"foo": 1, "bar": "abc", "buz": [1, 2]}
    """.trimIndent()
    val map = parseJson(json1)
    println(map)
    println(serialize(map))

    val map2: Map<String, Any> = mapOf(
        "foo" to 1,
        "bar" to "abc",
        "buz" to listOf(1, 2)
    )
    println(serialize(map2))

    val map3: Map<String, Any> = mapOf(
        "date" to Date()
    )
    println(serialize(map3))
}
