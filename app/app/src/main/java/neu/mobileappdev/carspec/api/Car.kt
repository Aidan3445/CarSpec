package com.cs4520.assignment5.models

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

sealed class Product {
    data class Equipment(val name: String, val price: Float) : Product()

    data class Food(val name: String, val expiryDate: String, val price: Float) : Product()
}

// deserializer to handle the different types of products
class ProductDeserializer : JsonDeserializer<Product> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Product {
        val jsonObject = json?.asJsonObject ?: throw IllegalArgumentException("Invalid product JSON")

        val name = jsonObject.get("name").asString // .substringBeforeLast('_')
        val price = jsonObject.get("price").asFloat

        return when (val type = jsonObject.get("type").asString) {
            "Equipment" -> {
                Product.Equipment(name, price)
            }
            "Food" -> {
                val expiryDate = jsonObject.get("expiryDate").asString
                Product.Food(name, expiryDate, price)
            }
            else -> throw IllegalArgumentException("Unknown product type: $type")
        }
    }
}
