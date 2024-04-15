package neu.mobileappdev.carspec.Database
import android.app.Application
import neu.mobileappdev.carspec.api.Car

class FavoriteRepository(application: Application) {
    private val database = AppDatabase.getInstance(application)
    private var carDao: CarDao = database.carDao()

    suspend fun favoriteCar(car: Car) {
        val favoriteCar = FavoriteCar(
            id = car.id,
            name = car.name,
            make = car.make,
            year = car.year,
            imageUrl = car.image
        )
        carDao.insertFavorite(favoriteCar)
    }

    suspend fun unfavoriteCar(car: Car) {
        val favoriteCar = FavoriteCar(
            id = car.id,
            name = car.name,
            make = car.make,
            year = car.year,
            imageUrl = car.image
        )
        carDao.deleteFavorite(favoriteCar)
    }

    suspend fun getFavoriteCarIds(): Set<Int> {
        val allCars = carDao.getAllFavorites()
        val favoriteIds = mutableSetOf<Int>()
        allCars.forEach { favoriteCar ->
            favoriteIds.add(favoriteCar.id)
        }
        return favoriteIds
    }

    suspend fun isCarFavorite(carId: Int): Boolean {
        val favoriteCar = carDao.getFavoriteById(carId)
        return favoriteCar != null
    }


    suspend fun getAllFavoriteCars(): List<Car> {
        val favoriteCars = carDao.getAllFavorites()
        return favoriteCars.map { favoriteCar ->
            Car(
                id = favoriteCar.id,
                name = favoriteCar.name,
                make = favoriteCar.make,
                year = favoriteCar.year,
                image = favoriteCar.imageUrl
            )
        }
    }

}
