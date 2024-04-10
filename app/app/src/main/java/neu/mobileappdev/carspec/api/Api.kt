package neu.mobileappdev.carspec.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL: String = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/api/"
    const val CARS: String = "cars"
    const val SPECS: String = "specs"

    private val gson =
        GsonBuilder()
            .registerTypeAdapter(Car::class.java, CarDeserializer())
            .registerTypeAdapter(Specs::class.java, SpecsDeserializer())
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
