package neu.mobileappdev.carspec.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neu.mobileappdev.carspec.api.ApiService
import neu.mobileappdev.carspec.api.Car
import neu.mobileappdev.carspec.api.CarQuery
import neu.mobileappdev.carspec.api.CarRepository

class HomeViewModel(
    private val repository: CarRepository = CarRepository(),
    private var query: CarQuery = CarQuery()
) : ViewModel() {
    // car data that is fetched from the API
    private val carListData = MutableLiveData<Set<Car>>(emptySet())
    val cars get() = carListData

    // fetch status
    private val isFetchingData = MutableLiveData(false)
    val isFetching get() = isFetchingData

    // error message
    private val errorMessageData = MutableLiveData("")
    val errorMessage get() = errorMessageData

    // model fetch call
    fun fetchCars() {
        isFetchingData.postValue(true)
        // fetch cars from the API
        viewModelScope.launch {
            try {
                // fetch cars from the API
                val response = repository.fetchCars(query)

                // handle empty data message
                if (response.isEmpty()) {
                    throw ApiService.FetchException("No cars available")
                }

                carListData.postValue(response)
                errorMessageData.postValue("")
            } catch (e: ApiService.FetchException) {
                errorMessageData.postValue(e.message)
            } catch (e: Exception) {
                errorMessageData.postValue("An error occurred while fetching data")
            } finally {
                isFetchingData.postValue(false)
            }
        }
    }

    fun setQuery(newQuery: CarQuery) {
        query = newQuery
    }

    // clear the query params
    fun clearQuery() {
        if (isFilterApplied()) {
            query = CarQuery()
            fetchCars()
        }
    }

    // bool is filter applied
    fun isFilterApplied(): Boolean {
        return query != CarQuery()
    }
}