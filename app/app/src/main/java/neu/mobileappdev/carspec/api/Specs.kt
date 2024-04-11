package neu.mobileappdev.carspec.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class Dimension(
    val length: Double,
    val width: Double,
    val height: Double,
)

class Specs(
    val id: Int = 0,
    val engine: String = "ERROR",
    val mileage: String = "ERROR",
    val dimensions: Dimension = Dimension(-1.0, -1.0, -1.0),
    val horsepower: Int = -1,
    val zeroToSixty: Double = -1.0,
)

// deserializer to handle the different types of products
class SpecsDeserializer : JsonDeserializer<Specs> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Specs {
        val jsonObject = json?.asJsonObject ?: throw IllegalArgumentException("Invalid product JSON")

        val id = jsonObject.get("id").asInt
        val engine = jsonObject.get("engine").asString
        val mileage = jsonObject.get("mileage").asString
        val dimensions = jsonObject.get("dimensions").asJsonObject
        val length = dimensions.get("length").asDouble
        val width = dimensions.get("width").asDouble
        val height = dimensions.get("height").asDouble
        val horsepower = jsonObject.get("horsepower").asInt
        val zeroToSixty = jsonObject.get("zeroToSixty").asDouble

        return Specs(id, engine, mileage, Dimension(length, width, height), horsepower, zeroToSixty)
    }
}
