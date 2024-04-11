package neu.mobileappdev.carspec.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neu.mobileappdev.carspec.api.Car
import neu.mobileappdev.carspec.api.CarQuery
import neu.mobileappdev.carspec.api.CarRepository

class HomeViewModel(
    private val repository: CarRepository = CarRepository(),
    application: Application = Application()
    ) : AndroidViewModel(application) {

    // query params that can be set in the search tab
    private val queryData = MutableLiveData<CarQuery>(null)
    val query get() = queryData

    // car data that is fetched from the API
    private val carData = MutableLiveData<Set<Car>>(emptySet())
    val cars get() = carData

    // fetch status
    private val isFetchingData = MutableLiveData<Boolean>(false)
    val isFetching get() = isFetchingData

    // error message
    private val errorMessageData = MutableLiveData<String>("")
    val errorMessage get() = errorMessageData

    // model fetch call
    fun fetchCars() {
        isFetchingData.postValue(true)
        // fetch cars from the API
        viewModelScope.launch {
            try {
                // fetch cars from the API
                val response = repository.fetchCars(queryData.value ?: CarQuery())

                Log.d("HomeViewModel", "fetchCars: $response")

                // handle empty data message
                if (response.isEmpty()) {
                    throw CarRepository.FetchException("No cars available")
                }

                carData.postValue(response)
                errorMessageData.postValue("")
            } catch (e: CarRepository.FetchException) {
                errorMessageData.postValue(e.message)
            } finally {
                isFetchingData.postValue(false)
            }
        }
    }

    // clear the query params
    fun clearQuery() {
        queryData.postValue(CarQuery())
    }
}