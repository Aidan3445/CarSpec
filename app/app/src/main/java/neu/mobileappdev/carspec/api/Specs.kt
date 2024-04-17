package neu.mobileappdev.carspec.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class Dimension(
    val length: Double,
    val width: Double,
    val height: Double,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Dimension

        return (length == other.length) &&
                (width == other.width) &&
                (height == other.height)
    }

    override fun hashCode(): Int {
        var result = 1
        result = 31 * result + length.hashCode()
        result = 31 * result + width.hashCode()
        result = 31 * result + height.hashCode()
        return result
    }

}

class Specs(
    val id: Int = 0,
    val engine: String = "ERROR",
    val mileage: String = "ERROR",
    val dimensions: Dimension = Dimension(-1.0, -1.0, -1.0),
    val horsepower: Int = -1,
    val zeroToSixty: Double = -1.0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Specs

        return (id == other.id) &&
                (engine == other.engine) &&
                (mileage == other.mileage) &&
                (dimensions == other.dimensions) &&
                (horsepower == other.horsepower) &&
                (zeroToSixty == other.zeroToSixty)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + engine.hashCode()
        result = 31 * result + mileage.hashCode()
        result = 31 * result + dimensions.hashCode()
        result = 31 * result + horsepower.hashCode()
        result = 31 * result + zeroToSixty.hashCode()
        return result
    }

}

// deserializer to handle the different types of products
class SpecsDeserializer : JsonDeserializer<Specs> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Specs {
        val jsonObject =
            json?.asJsonObject ?: throw IllegalArgumentException("Invalid product JSON")

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
