package neu.mobileappdev.carspec.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class Car(
    val id: Int,
    val name: String,
    val make: String,
    val year: Int,
    val image: String,
)

// deserializer to handle the different types of products
class ProductDeserializer : JsonDeserializer<Car> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Car {
        val jsonObject = json?.asJsonObject ?: throw IllegalArgumentException("Invalid product JSON")

        val id = jsonObject.get("id").asInt
        val name = jsonObject.get("name").asString
        val make = jsonObject.get("make").asString
        val year = jsonObject.get("year").asInt
        val image = jsonObject.get("image").asString

        return Car(id, name, make, year, image)
    }
}
