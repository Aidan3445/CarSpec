package neu.mobileappdev.carspec.ui.car

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neu.mobileappdev.carspec.api.ApiService
import neu.mobileappdev.carspec.api.Car
import neu.mobileappdev.carspec.api.CarRepository
import neu.mobileappdev.carspec.api.Specs

class CarViewModel(
    private val carID: Int,
    private val carRepository: CarRepository
) : ViewModel() {

    private val _car = MutableLiveData<Car>()
    val car get() = _car

    private val _carSpecs = MutableLiveData<Specs>()
    val carSpecs get() = _carSpecs

    private val _errorMessage = MutableLiveData("")
    val errorMessage get() = _errorMessage

    fun fetchCar() {
        // fetch car from the API
        viewModelScope.launch {
            try {
                // fetch car from the API
                val response = carRepository.fetchCar(carID)

                car.postValue(response)
                errorMessage.postValue("")
            } catch (e: ApiService.FetchException) {
                errorMessage.postValue(e.message)
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            }
        }
    }


    fun fetchSpecs() {
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
                errorMessage.postValue(e.message)
            }
        }
    }

}