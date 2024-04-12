package neu.mobileappdev.carspec.api

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class LoginResponse(
    val success: Boolean = false,
    val hint: String = "",
)

// deserializer to handle the different types of products
class LoginResponseDeserializer : JsonDeserializer<LoginResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): LoginResponse {
        val jsonObject =
            json?.asJsonObject ?: throw IllegalArgumentException("Invalid product JSON")

        Log.d("LoginResponseDeserializer", "deserialize: $jsonObject")

        val success = jsonObject.get("success")?.asBoolean ?: false
        Log.d("LoginResponseDeserializer", "deserialize: $success")
        val hint = jsonObject.get("hint")?.asString ?: ""
        Log.d("LoginResponseDeserializer", "deserialize: $hint")

        return LoginResponse(success, hint)
    }
}
