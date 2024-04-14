package neu.mobileappdev.carspec.ui.car

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neu.mobileappdev.carspec.Database.FavoriteRepository
import neu.mobileappdev.carspec.api.ApiService
import neu.mobileappdev.carspec.api.Car
import neu.mobileappdev.carspec.api.CarRepository
import neu.mobileappdev.carspec.api.Specs

class CarViewModel(
    private val carID: Int,
    private val carRepository: CarRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val _car = MutableLiveData<Car>()
    val car get() = _car

    private val _carSpecs = MutableLiveData<Specs>()
    val carSpecs get() = _carSpecs


    private val _isFetchingData = MutableLiveData(false)
    val isFetchingData get() = _isFetchingData

    private val _isFavorite = MutableLiveData(false)
    val isFavorite get() = _isFavorite

    private val _errorMessage = MutableLiveData("")
    val errorMessage get() = _errorMessage

    init {
        fetchCar(carID)
        fetchFavoriteStatus(carID)
        fetchSpecs(carID)
    }

    private fun fetchCar(carID: Int) {
        isFetchingData.postValue(true)
        // fetch car from the API
        viewModelScope.launch {
            try {
                // fetch car from the API
                Log.d("HI", carID.toString())
                val response = carRepository.fetchCar(carID)

                Log.d("fetched", "fetched")
                car.postValue(response)
                errorMessage.postValue("")
            } catch (e: ApiService.FetchException) {
                errorMessage.postValue(e.message)
            } catch (e: Exception) {
                errorMessage.postValue("An error occurred while fetching data, $e")
            } finally {
                isFetchingData.postValue(false)
            }
        }
    }

    private fun fetchFavoriteStatus(carID: Int) {
        viewModelScope.launch {
            val isFavoriteResult = favoriteRepository.isCarFavorite(carID)
            isFavorite.postValue(isFavoriteResult)
        }
    }

    fun toggleFavorite(car: Car) {
        viewModelScope.launch {
            val isFavoriteResult = favoriteRepository.isCarFavorite(car.id)
            if (isFavoriteResult) {
                car.let { favoriteRepository.unfavoriteCar(car) }
                isFavorite.postValue(false)
            } else {
                car.let { favoriteRepository.favoriteCar(car) }
                isFavorite.postValue(true)
            }
        }
    }

    private fun fetchSpecs(carID: Int) {
        isFetchingData.postValue(true)
        // fetch specs from the API
        viewModelScope.launch {
            try {
                // fetch specs from the API
                val response = carRepository.fetchSpecs(carID)
                carSpecs.postValue(response)
                errorMessage.postValue("")
            } catch (e: ApiService.FetchException) {
                errorMessage.postValue(e.message)
            } catch (e: Exception) {
                errorMessage.postValue("An error occurred while fetching data")
            } finally {
                isFetchingData.postValue(false)
            }
        }
    }

}