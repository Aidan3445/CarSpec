package neu.mobileappdev.carspec.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CarRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: CarRepository
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = FakeApiService()
        repository = CarRepository(apiService)
    }

    @Test
    fun fetchCarsNoQuery() = runTest {
        // given
        val query = CarQuery()

        // when
        val result = repository.fetchCars(query)

        // then
        assertEquals(
            setOf(
                Car(1, "Car1", "Make1", 2021),
                Car(2, "Car2", "Make2", 2022),
                Car(3, "Car3", "Make3", 2023),
                Car(4, "Car4", "Make4", 2024),
                Car(5, "Car5", "Make5", 2025),
            ), result
        )
    }

    @Test
    fun fetchCarsWithQuery() = runTest {
        // given
        val query = CarQuery(name = "Car1", make = "Make1", year = 2021)

        // when
        val result = repository.fetchCars(query)

        // then
        assertEquals(
            setOf(
                Car(1, "Car1", "Make1", 2021),
            ), result
        )
    }

    @Test
    fun fetchCarsServerError() = runTest {
        // given
        apiService = FailApiService()
        repository = CarRepository(apiService)

        try {
            // when
            repository.fetchCars(CarQuery())
        } catch (e: ApiService.FetchException) {
            // then
            assertEquals("Server Error", e.message)
        }
    }

    @Test
    fun fetchCarSuccess() = runTest {
        // given
        val id = 3

        // when
        val result = repository.fetchCar(id)

        // then
        assertEquals(Car(3, "Car3", "Make3", 2023), result)
    }

    @Test
    fun fetchCarNotFound() = runTest {
        // given
        val id = 6

        try {
            // when
            repository.fetchCar(id)
        } catch (e: ApiService.FetchException) {
            // then
            assertEquals("Car not found", e.message)
        }
    }

    @Test
    fun fetchCarError() = runTest {
        // given
        apiService = FailApiService()
        repository = CarRepository(apiService)
        val id = 2

        try {
            // when
            repository.fetchCar(id)
        } catch (e: ApiService.FetchException) {
            // then
            assertEquals("Server Error", e.message)
        }
    }

    @Test
    fun fetchSpecsSuccess() = runTest {
        // given
        val id = 4

        // when
        val result = repository.fetchSpecs(id)

        // then
        assertEquals(4, result.id)
        assertEquals("Engine4", result.engine)
        assertEquals("4 mpg", result.mileage)
        assertEquals(Dimension(4.0, 4.4, 4.44), result.dimensions)
        assertEquals(444, result.horsepower)
        assertEquals(4.4, result.zeroToSixty, 0.001)
    }

    @Test
    fun fetchSpecsNotFound() = runTest {
        // given
        val id = 6

        try {
            // when
            repository.fetchSpecs(id)
        } catch (e: ApiService.FetchException) {
            // then
            assertEquals("Car not found", e.message)
        }
    }

    @Test
    fun fetchSpecsError() = runTest {
        // given
        apiService = FailApiService()
        repository = CarRepository(apiService)
        val id = 2

        try {
            // when
            repository.fetchSpecs(id)
        } catch (e: ApiService.FetchException) {
            // then
            assertEquals("Server Error", e.message)
        }
    }
}