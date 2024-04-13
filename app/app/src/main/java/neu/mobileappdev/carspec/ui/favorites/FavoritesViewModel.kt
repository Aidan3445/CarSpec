package neu.mobileappdev.carspec.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neu.mobileappdev.carspec.Database.FavoriteRepository
import neu.mobileappdev.carspec.api.Car

class FavoritesViewModel(application: Application) : AndroidViewModel(application)  {
    private val repository: FavoriteRepository = FavoriteRepository(application)
    private val _favoriteCars = MutableLiveData<Set<Int>>()
    val favoriteCars: LiveData<Set<Int>> = _favoriteCars

    init {
        loadFavorites()
    }
    fun toggleFavorite(car: Car) {
        viewModelScope.launch {
            if (_favoriteCars.value?.contains(car.id) == true) {
                repository.unfavoriteCar(car)
                _favoriteCars.value = _favoriteCars.value?.minus(car.id)
            } else {
                repository.favoriteCar(car)
                _favoriteCars.value = _favoriteCars.value?.plus(car.id)
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            // Assuming repository.getFavoriteCarIds() fetches IDs from the database
            val favorites = repository.getFavoriteCarIds()
            _favoriteCars.value = favorites.toSet()
        }
    }

    fun isCarFavorite(carId: Int): Boolean {
        return _favoriteCars.value?.contains(carId) ?: false
    }

}