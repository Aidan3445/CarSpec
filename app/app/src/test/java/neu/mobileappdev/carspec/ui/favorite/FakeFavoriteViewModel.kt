package neu.mobileappdev.carspec.Database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neu.mobileappdev.carspec.api.Car

class FakeFavoriteViewModel : ViewModel() {
    private val repository: FakeFavoriteRepository = FakeFavoriteRepository()
    private val _favoriteCars = MutableLiveData<Set<Int>>(setOf())
    val favoriteCars: LiveData<Set<Int>> = _favoriteCars
    private val _isLoading = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    private val _favCars = MutableLiveData<List<Car>>()
    val favCars: LiveData<List<Car>> = _favCars

    init {
        loadFavorites()
        getAllFavorites()
    }

    fun toggleFavorite(car: Car) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val isFavoriteResult = repository.isCarFavorite(car.id)
                if (isFavoriteResult) {
                    repository.unfavoriteCar(car)
                    val newSet = _favoriteCars.value.orEmpty().minus(car.id)
                    _favoriteCars.postValue(newSet)
                } else {
                    repository.favoriteCar(car)
                    val newSet = _favoriteCars.value.orEmpty().plus(car.id)
                    _favoriteCars.postValue(newSet)
                }
            } catch (e: Exception) {
                _error.postValue("Failed to update favorites: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val favorites = repository.getFavoriteCarIds()
                _favoriteCars.value = favorites.toSet()
            } catch (e: Exception) {
                _error.value = "Failed to load favorites: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getAllFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val favorites = repository.getAllFavoriteCars()
                _favCars.value = favorites
            } catch (e: Exception) {
                _error.value = "Failed to load favorites: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}




