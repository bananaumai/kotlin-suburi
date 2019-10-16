import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.coroutines.awaitObjectResponseResult
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    ip()
    cityWeather()
}

suspend fun ip() {
    val jsonMapper = jacksonObjectMapper()
    val url = "https://api.ipify.org?format=json"
    val (request, response, result) = Fuel.get(url).awaitStringResponseResult()

    println(request)
    println(response)

    result.fold(
        {
            println(it)

            val jsonObject = jsonMapper.readTree(it)

            println("ip : ${jsonObject.get("ip").asText()}")
        },
        { println(it) }
    )
}

data class Weather(val id: Long, val main: String)
data class Coord(val lon: Double, val lat: Double)
data class City(val coord: Coord, val weather: List<Weather>)

object CityDeserializer : ResponseDeserializable<City> {
    override fun deserialize(content: String): City? {
        val jsonMapper = jacksonObjectMapper()
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return jsonMapper.readValue<City>(content)
    }
}

suspend fun cityWeather() {
    val url = "https://samples.openweathermap.org/data/2.5/weather?id=2172797&appid=b6907d289e10d714a6e88b30761fae22"
    val (request, response, result) = Fuel.get(url).awaitObjectResponseResult(CityDeserializer)

    println(request)
    println(response)

    result.fold(
        { println(it) },
        { println(it) }
    )
}

