package neu.mobileappdev.carspec.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import neu.mobileappdev.carspec.api.ApiService
import neu.mobileappdev.carspec.api.Car
import neu.mobileappdev.carspec.api.CarQuery
import neu.mobileappdev.carspec.api.CarRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: CarRepository
    private lateinit var carObserver: Observer<Set<Car>>
    private lateinit var fetchingObserver: Observer<Boolean>
    private lateinit var messageObserver: Observer<String>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        repository = FakeRepo()
        viewModel = HomeViewModel(repository)
        carObserver = Observer {  }
        fetchingObserver = Observer {  }
        messageObserver = Observer {  }
        viewModel.cars.observeForever(carObserver)
        viewModel.isFetching.observeForever(fetchingObserver)
        viewModel.errorMessage.observeForever(messageObserver)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun fetchCarsNoQuery() {
        // preconditions
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(emptySet<Car>(), viewModel.cars.value)

        // when
        viewModel.fetchCars()

        // then
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(setOf(
            Car(1, "Car1", "Make1", 2021),
            Car(2, "Car2", "Make2", 2022),
            Car(3, "Car3", "Make3", 2023),
            Car(4, "Car4", "Make4", 2024),
            Car(5, "Car5", "Make5", 2025),
        ),
            viewModel.cars.value)
    }

    @Test
    fun fetchCarsWithQuery() {
        // given
        viewModel = HomeViewModel(FakeRepo(), CarQuery(name = "Car1"))

        // preconditions
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(emptySet<Car>(), viewModel.cars.value)

        // when
        viewModel.fetchCars()

        // then
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(setOf(Car(1, "Car1", "Make1", 2021)), viewModel.cars.value)
    }

    @Test
    fun fetchCarsWithQueryNoResults() {
        // given
        viewModel = HomeViewModel(FakeRepo(), CarQuery(name = "Car6"))

        // preconditions
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(emptySet<Car>(), viewModel.cars.value)

        // when
        viewModel.fetchCars()

        // then
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("No cars available", viewModel.errorMessage.value)
        assertEquals(emptySet<Car>(), viewModel.cars.value)
    }

    @Test
    fun fetchCarsWithFetchException() {
        // given
        viewModel = HomeViewModel(FakeFetchExceptionRepo())

        // preconditions
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(emptySet<Car>(), viewModel.cars.value)

        // when
        viewModel.fetchCars()

        // then
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("Fetch Exception", viewModel.errorMessage.value)
        assertEquals(emptySet<Car>(), viewModel.cars.value)
    }

    @Test
    fun fetchCarsWithOtherException() {
        // given
        viewModel = HomeViewModel(FakeOtherExceptionRepo())

        // preconditions
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(emptySet<Car>(), viewModel.cars.value)

        // when
        viewModel.fetchCars()

        // then
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("An error occurred while fetching data", viewModel.errorMessage.value)
        assertEquals(emptySet<Car>(), viewModel.cars.value)
    }

    @Test
    fun setQuery() {
        // given
        val query = CarQuery(name = "Car5")

        // preconditions
        viewModel.fetchCars()
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(setOf(
            Car(1, "Car1", "Make1", 2021),
            Car(2, "Car2", "Make2", 2022),
            Car(3, "Car3", "Make3", 2023),
            Car(4, "Car4", "Make4", 2024),
            Car(5, "Car5", "Make5", 2025),
        ), viewModel.cars.value)

        // when
        viewModel.setQuery(query)
        viewModel.fetchCars()

        // then
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(setOf(Car(5, "Car5", "Make5", 2025)), viewModel.cars.value)
    }

    @Test
    fun clearQuery() {
        // given
        val query = CarQuery(name = "Car5")

        // preconditions
        viewModel.setQuery(query)
        viewModel.fetchCars()
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(setOf(Car(5, "Car5", "Make5", 2025),), viewModel.cars.value)

        // when
        viewModel.clearQuery()

        // then
        assertEquals(false, viewModel.isFetching.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(setOf(
            Car(1, "Car1", "Make1", 2021),
            Car(2, "Car2", "Make2", 2022),
            Car(3, "Car3", "Make3", 2023),
            Car(4, "Car4", "Make4", 2024),
            Car(5, "Car5", "Make5", 2025),
        ), viewModel.cars.value)
    }

    @Test
    fun isFilterApplied() {
        // given
        val query = CarQuery(name = "Car5")

        // preconditions
        viewModel.setQuery(query)
        assertEquals(true, viewModel.isFilterApplied())

        // when
        viewModel.clearQuery()

        // then
        assertEquals(false, viewModel.isFilterApplied())
    }
}

class FakeRepo : CarRepository() {
    private val cars = setOf(
        Car(1, "Car1", "Make1", 2021),
        Car(2, "Car2", "Make2", 2022),
        Car(3, "Car3", "Make3", 2023),
        Car(4, "Car4", "Make4", 2024),
        Car(5, "Car5", "Make5", 2025),
    )

    override suspend fun fetchCars(query: CarQuery): Set<Car> {
        return cars.filter {
                    (query.name.isNullOrEmpty() || it.name == query.name) &&
                    (query.make.isNullOrEmpty() || it.make == query.make) &&
                    (query.year == null || it.year == query.year)
        }.toSet()
    }
}

class FakeFetchExceptionRepo : CarRepository() {
    override suspend fun fetchCars(query: CarQuery): Set<Car> {
        throw ApiService.FetchException("Fetch Exception")
    }
}

class FakeOtherExceptionRepo : CarRepository() {
    override suspend fun fetchCars(query: CarQuery): Set<Car> {
        throw Exception("Other Exception")
    }
}