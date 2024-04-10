package com.cs4520.assignment5.api

import com.cs4520.assignment5.models.Product
import com.cs4520.assignment5.models.ProductDeserializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL: String = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/"
    const val ENDPOINT: String = "prod"
    const val RAND: String = "prod/random"

    private val gson =
        GsonBuilder()
            .registerTypeAdapter(Product::class.java, ProductDeserializer())
            .create()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}

object Api {
    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}
