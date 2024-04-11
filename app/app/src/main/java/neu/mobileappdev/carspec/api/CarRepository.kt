package neu.mobileappdev.carspec.api

import org.json.JSONObject

class CarRepository {
    class FetchException(message: String) : Exception(message)

    // fetch cars from api
    suspend fun fetchCars(query: CarQuery): Set<Car> {
        val response = Api.apiService.getCars(query.name, query.make, query.year)

        // handle response
        if (response.isSuccessful) {
            return response.body() ?: emptySet()
        } else {
            val errorObj = response.errorBody()?.string()
            // get error message
            val err =
                try {
                    val json = errorObj?.let { JSONObject(it) }
                    json?.getString("message")
                } catch (e: Exception) {
                    e.message
                }

            // throw custom error type for better handling
            throw FetchException(err ?: "Unknown error")
        }
    }

    // fetch specs for a car from ID
    suspend fun fetchSpecs(id: Int): Specs {
        val response = Api.apiService.getSpecs(id)

        // handle response
        if (response.isSuccessful) {
            return response.body() ?: Specs()
        } else {
            val errorObj = response.errorBody()?.string()
            // get error message
            val err =
                try {
                    val json = errorObj?.let { JSONObject(it) }
                    json?.getString("message")
                } catch (e: Exception) {
                    e.message
                }

            // throw custom error type for better handling
            throw FetchException(err ?: "Unknown error")
        }
    }

    // add and remove favorite cars from room database
    suspend fun addFavoriteCar(
        car: Car,
    ) {
        // TODO
    }

    suspend fun removeFavoriteCar(
        car: Car,
    ) {
        // TODO
    }
}
