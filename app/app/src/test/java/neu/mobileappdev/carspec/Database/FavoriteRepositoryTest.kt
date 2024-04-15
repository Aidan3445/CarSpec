package neu.mobileappdev.carspec.Database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import neu.mobileappdev.carspec.api.Car
import neu.mobileappdev.carspec.api.CarQuery
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FakeCarDao : CarDao {
    var fakeFavoriteCars = mutableListOf<FavoriteCar>()
    var fakeFavoriteCarsLiveData = MutableLiveData<List<FavoriteCar>>()

    override suspend fun insertFavorite(car: FavoriteCar) {
        fakeFavoriteCars.add(car)
        updateLiveData()
    }

    override suspend fun deleteFavorite(car: FavoriteCar) {
        fakeFavoriteCars.remove(car)
        updateLiveData()
    }

    override suspend fun getAllFavorites(): List<FavoriteCar> = fakeFavoriteCars

    override suspend fun getFavoriteById(carId: Int): FavoriteCar? =
        fakeFavoriteCars.find { it.id == carId }

    private fun updateLiveData() {
        fakeFavoriteCarsLiveData.postValue(fakeFavoriteCars.toList())
    }

}

class FakeFavoriteRepository() {
    private var carDao: FakeCarDao = FakeCarDao()

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



class FavoriteRepositoryTest {
    private lateinit var fakeCarDao: FakeCarDao
    private lateinit var repository: FakeFavoriteRepository
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Before
    fun setup() {
        fakeCarDao = FakeCarDao()
        repository = FakeFavoriteRepository()
    }

    @Test
    fun favoriteCarTest() = runTest {
        val car =  Car(1, "Car1", "Make1", 2021)
        repository.favoriteCar(car)
        Assert.assertEquals( repository.isCarFavorite(car.id), true)
        Assert.assertEquals( repository.isCarFavorite(2), false)
    }
    @Test
    fun unfavoriteCarTest() = runTest {
        val car =  Car(1, "Car1", "Make1", 2021)
        repository.favoriteCar(car)
        Assert.assertEquals( repository.isCarFavorite(car.id), true)
        Assert.assertEquals( repository.isCarFavorite(2), false)
    }

}


