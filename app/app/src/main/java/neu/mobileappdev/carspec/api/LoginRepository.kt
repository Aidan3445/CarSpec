package neu.mobileappdev.carspec.api

import org.json.JSONObject

open class LoginRepository(
    private val apiService: ApiService = Api.apiService
) {
    // try login
    open suspend fun login(username: String, password: String): Boolean {
        val response = apiService.login(username, password)

        // handle response
        if (response.isSuccessful) {
            return response.body()?.success ?: false
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

    // get login hint
    open suspend fun getHint(): String {
        val response = apiService.getHint()

        // handle response
        if (response.isSuccessful) {
            return response.body()?.hint ?: ""
        } else {
            val errorObj = response.errorBody()?.string()

            // get error message
            val err =
                try {
                    val json = errorObj?.let { JSONObject(it) }
                    json?.getString("error")
                } catch (e: Exception) {
                    //e.message
                    errorObj
                }

            // return error message
            return err ?: "Unknown error while getting hint"
        }
    }
}
