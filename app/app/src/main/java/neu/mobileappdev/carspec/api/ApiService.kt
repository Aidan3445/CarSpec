package neu.mobileappdev.carspec.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // get 25 cars
    @GET(RetrofitClient.CARS)
    suspend fun getCars(
        @Query("name") make: String = "",
        @Query("make") model: String = "",
        @Query("year") year: String = "",
    ): Response<Set<Car>>

    // get the specs for a car from the id
    @GET(RetrofitClient.SPECS)
    suspend fun getSpecs(
        @Path("id") id: String,
    ): Response<Specs>
}
