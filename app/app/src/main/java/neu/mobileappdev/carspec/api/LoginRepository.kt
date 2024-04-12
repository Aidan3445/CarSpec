package neu.mobileappdev.carspec.api

import android.util.Log
import org.json.JSONObject

class LoginRepository {
    // try login
    suspend fun login(username: String, password: String): Boolean {
        Log.d("LoginRepository", "R: $username, $password")

        val response = Api.apiService.login(username, password)

        Log.d("LoginRepository", "login: ${response.isSuccessful}")
        Log.d("LoginRepository", "login: $response")

        // handle response
        if (response.isSuccessful) {
            Log.d("LoginRepository", "login: ${response.body()?.success}")
            Log.d("LoginRepository", "login: ${response.body()?.hint}")
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
    suspend fun getHint(): String {
        val response = Api.apiService.getHint()

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
                    e.message
                }

            // throw custom error type for better handling
            throw ApiService.FetchException(err ?: "Unknown error")
        }
    }
}
