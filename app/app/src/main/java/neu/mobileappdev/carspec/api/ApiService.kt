package neu.mobileappdev.carspec.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // get 25 cars
    @GET(RetrofitClient.CARS)
    suspend fun getCars(
        @Query("name") name: String? = null,
        @Query("make") make: String? = null,
        @Query("year") year: Int? = null,
    ): Response<Set<Car>>

    // get car by id
    @GET(RetrofitClient.CARS)
    suspend fun getCar(
        @Path("id") id: Int,
    ): Response<Car>

    // get the specs for a car from the id
    @GET(RetrofitClient.SPECS)
    suspend fun getSpecs(
        @Path("id") id: Int,
    ): Response<Specs>
}
