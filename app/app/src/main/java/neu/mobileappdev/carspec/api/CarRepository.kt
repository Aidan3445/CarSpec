package neu.mobileappdev.carspec.api

import org.json.JSONObject

open class CarRepository(
    private val apiService: ApiService = Api.apiService
){
    // fetch cars from api
    open suspend fun fetchCars(query: CarQuery): Set<Car> {
        val response = apiService.getCars(query.name, query.make, query.year)

        // handle response
        if (response.isSuccessful) {
            return response.body() ?: emptySet()
        } else {
            val errorObj = response.errorBody()?.string()
            // get error message
            val err =
                try {
                    val json = errorObj?.let { JSONObject(it) }
                    json?.getString("error")
                } catch (e: Exception) {
                    e.message
                }

            // throw custom error type for better handling
            throw ApiService.FetchException(err ?: "Unknown error")
        }
    }

    // fetch a car from ID
    open suspend fun fetchCar(id: Int): Car {
        val response = apiService.getCar(id)

        // handle response
        if (response.isSuccessful) {
            return response.body() ?: Car()
        } else {
            val errorObj = response.errorBody()?.string()
            // get error message
            val err =
                try {
                    val json = errorObj?.let { JSONObject(it) }
                    json?.getString("error")
                } catch (e: Exception) {
                    e.message
                }

            // throw custom error type for better handling
            throw ApiService.FetchException(err ?: "Unknown error")
        }
    }

    // fetch specs for a car from ID
    open suspend fun fetchSpecs(id: Int): Specs {
        val response = apiService.getSpecs(id)
        // handle response
        if (response.isSuccessful) {
            return response.body() ?: Specs()
        } else {
            val errorObj = response.errorBody()?.string()
            // get error message
            val err =
                try {
                    val json = errorObj?.let { JSONObject(it) }
                    json?.getString("error")
                } catch (e: Exception) {
                    e.message
                }

            // throw custom error type for better handling
            throw ApiService.FetchException(err ?: "Unknown error")
        }
    }
}
