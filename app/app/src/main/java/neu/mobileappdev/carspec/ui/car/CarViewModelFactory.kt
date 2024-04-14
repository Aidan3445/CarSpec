package neu.mobileappdev.carspec.ui.car

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.app.Application
import neu.mobileappdev.carspec.Database.FavoriteRepository
import neu.mobileappdev.carspec.api.CarRepository

class CarViewModelFactory(
    private val carID: Int,
    private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            val carRepository = CarRepository()
            val favoriteRepository = FavoriteRepository(app)
            return CarViewModel(carID, carRepository, favoriteRepository) as T
        }
        throw IllegalArgumentException("Not CarViewModel class...")
    }
}