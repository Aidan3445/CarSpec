package neu.mobileappdev.carspec.Database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.test.runTest
import neu.mobileappdev.carspec.api.Car
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

class FakeFavoriteRepository {
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
        val car = Car(1, "Car1", "Make1", 2021)
        val car2 = Car(2, "Car1", "Make1", 2021)
        repository.favoriteCar(car)
        Assert.assertEquals(repository.isCarFavorite(car.id), true)
        Assert.assertEquals(repository.isCarFavorite(car2.id), false)
    }

    @Test
    fun unfavoriteCarTest() = runTest {
        val car3 = Car(2, "Car1", "Make1", 2021)
        Assert.assertEquals(repository.isCarFavorite(car3.id), false)
        repository.favoriteCar(car3)
        Assert.assertEquals(repository.isCarFavorite(car3.id), true)
        repository.unfavoriteCar(car3)
        Assert.assertEquals(repository.isCarFavorite(car3.id), false)
    }

    @Test
    fun getFavoriteCarIdsTest() = runTest {
        val car = Car(1, "Car1", "Make1", 2021)
        val car2 = Car(2, "Car1", "Make1", 2021)
        val car3 = Car(3, "Car1", "Make1", 2021)
        val car4 = Car(4, "Car1", "Make1", 2021)

        // Favorite some cars
        repository.favoriteCar(car)
        repository.favoriteCar(car2)
        repository.favoriteCar(car3)

        val favoriteIds = repository.getFavoriteCarIds()

        assertTrue(favoriteIds.contains(car.id))
        assertTrue(favoriteIds.contains(car2.id))
        assertTrue(favoriteIds.contains(car3.id))
        assertFalse(favoriteIds.contains(car4.id))
        assertTrue(repository.isCarFavorite(car3.id))
        repository.unfavoriteCar(car3)
        assertFalse(repository.isCarFavorite(car3.id))

        val updatedFavoriteIds = repository.getFavoriteCarIds()
        assertFalse(updatedFavoriteIds.contains(car3.id))
    }

    @Test
    fun getFavoriteCarTest() = runTest {
        val car = Car(1, "Car1", "Make1", 2021)
        val car2 = Car(2, "Car1", "Make1", 2021)
        val car3 = Car(3, "Car1", "Make1", 2021)
        val car4 = Car(4, "Car1", "Make1", 2021)

        // Favorite some cars
        repository.favoriteCar(car)
        repository.favoriteCar(car2)
        repository.favoriteCar(car3)

        val favoritecar = repository.getAllFavoriteCars()

        assertTrue(favoritecar.contains(car))
        assertTrue(favoritecar.contains(car2))
        assertTrue(favoritecar.contains(car3))
        assertFalse(favoritecar.contains(car4))
    }


}


