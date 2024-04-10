package neu.mobileappdev.carspec.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import neu.mobileappdev.carspec.api.Car

class HomeViewModel : ViewModel() {

    // query params that can be set in the search tab
    private val queryData = MutableLiveData<List<String>>()
    val query get() = queryData

    // car data that is fetched from the API
    private val carData = MutableLiveData<Set<Car>>()
    val cars get() = carData

    // fetch status
    private val isFetchingData = MutableLiveData<Boolean>()
    val isFetching get() = isFetchingData

    // error message
    private val errorMessageData = MutableLiveData<String>()
    val errorMessage get() = errorMessageData

    // model fetch call
    fun fetchCars() {
        isFetchingData.postValue(true)
        // fetch cars from the API
        // TODO implement fetchCars
    }
}