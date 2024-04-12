package neu.mobileappdev.carspec.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class Car(
    val id: Int = 0,
    val name: String = "Car Name",
    val make: String = "Car Make",
    val year: Int = 9999,
    val image: String = "https://via.placeholder.com/150"
)

// deserializer to handle the different types of products
class CarDeserializer : JsonDeserializer<Car> {
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
        val image = jsonObject.get("img").asString

        return Car(id, name, make, year, image)
    }
}

data class CarQuery(
    val name: String? = null,
    val make: String? = null,
    val year: Int? = null,
)
