package neu.mobileappdev.carspec.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neu.mobileappdev.carspec.Database.FavoriteRepository
import neu.mobileappdev.carspec.api.Car

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository =  FavoriteRepository(application)
    private val _favoriteCars = MutableLiveData<Set<Int>>(setOf())
    val favoriteCars: LiveData<Set<Int>> = _favoriteCars
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
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
            _isLoading.value = true
            try {
                if (_favoriteCars.value?.contains(car.id) == true) {
                    repository.unfavoriteCar(car)
                    _favoriteCars.value = _favoriteCars.value?.minus(car.id)
                } else {
                    repository.favoriteCar(car)
                    _favoriteCars.value = _favoriteCars.value?.plus(car.id)
                }
            } catch (e: Exception) {
                _error.value = "Failed to update favorites: ${e.message}"
            } finally {
                _isLoading.value = false
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

   private fun getAllFavorites() {
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
