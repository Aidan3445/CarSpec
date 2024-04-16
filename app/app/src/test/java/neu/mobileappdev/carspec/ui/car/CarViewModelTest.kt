package neu.mobileappdev.carspec.ui.car

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import neu.mobileappdev.carspec.api.ApiService
import neu.mobileappdev.carspec.api.Car
import neu.mobileappdev.carspec.api.CarRepository
import neu.mobileappdev.carspec.api.Dimension
import neu.mobileappdev.carspec.api.Specs
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CarViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: CarViewModel
    private lateinit var repository: CarRepository
    private lateinit var carObserver: Observer<Car>
    private lateinit var carSpecsObserver: Observer<Specs>
    private lateinit var messageObserver: Observer<String>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        repository = FakeCarRepo()
        viewModel = CarViewModel(1, repository)
        carObserver = Observer { }
        carSpecsObserver = Observer { }
        messageObserver = Observer { }
        viewModel.car.observeForever(carObserver)
        viewModel.carSpecs.observeForever(carSpecsObserver)
        viewModel.errorMessage.observeForever(messageObserver)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun fetchCarTest() {
        // preconditions
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)

        // when
        viewModel.fetchCar()

        // then
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("", viewModel.errorMessage.value)
        Assert.assertEquals(
            Car(1, "Car1", "Make1", 2021),
            viewModel.car.value
        )
    }

    @Test
    fun fetchCarNonexistentCar() {
        // given
        viewModel = CarViewModel(6, repository)

        // preconditions
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)

        // when
        viewModel.fetchCar()

        // then
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("Car with ID 6 not found.", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)
    }

    @Test
    fun fetchSpecs() {
        // given
        viewModel = CarViewModel(1, repository)

        // preconditions
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)

        // when
        viewModel.fetchSpecs()

        // then
        Assert.assertEquals(
            Specs(
                1, "engine1", "mileage1", Dimension(1.0, 1.0, 1.0),
                1, 1.0
            ), viewModel.carSpecs.value
        )
        Assert.assertEquals("", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)
    }

    @Test
    fun fetchSpecsNonexistentCar() {
        // given
        viewModel = CarViewModel(6, repository)

        // preconditions
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)

        // when
        viewModel.fetchSpecs()

        // then
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("Specs with ID 6 not found.", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)
    }

    @Test
    fun fetchCarWithFetchException() {
        // given
        viewModel = CarViewModel(1, FakeFetchExceptionRepo())

        // preconditions
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)

        // when
        viewModel.fetchCar()

        // then
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("Fetch Exception", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)
    }

    @Test
    fun fetchCarWithOtherException() {
        // given
        viewModel = CarViewModel(1, FakeOtherExceptionRepo())

        // preconditions
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)

        // when
        viewModel.fetchCar()

        // then
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("Other Exception", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)
    }

    @Test
    fun fetchSpecsWithFetchException() {
        // given
        viewModel = CarViewModel(1, FakeFetchExceptionRepo())

        // preconditions
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)

        // when
        viewModel.fetchSpecs()

        // then
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("Fetch Exception", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)
    }

    @Test
    fun fetchSpecsWithOtherException() {
        // given
        viewModel = CarViewModel(1, FakeOtherExceptionRepo())

        // preconditions
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)

        // when
        viewModel.fetchSpecs()

        // then
        Assert.assertEquals(null, viewModel.carSpecs.value)
        Assert.assertEquals("Other Exception", viewModel.errorMessage.value)
        Assert.assertEquals(null, viewModel.car.value)
    }
}

class FakeCarRepo : CarRepository() {
    private val cars = setOf(
        Car(1, "Car1", "Make1", 2021),
        Car(2, "Car2", "Make2", 2022),
        Car(3, "Car3", "Make3", 2023),
        Car(4, "Car4", "Make4", 2024),
        Car(5, "Car5", "Make5", 2025),
    )

    private val specs = setOf(
        Specs(
            1, "engine1", "mileage1", Dimension(1.0, 1.0, 1.0),
            1, 1.0
        ),
        Specs(
            2, "engine2", "mileage2", Dimension(2.0, 2.0, 2.0),
            2, 2.0
        ),
        Specs(
            3, "engine3", "mileage3", Dimension(3.0, 3.0, 3.0),
            3, 3.0
        ),
        Specs(
            4, "engine4", "mileage4", Dimension(4.0, 4.0, 4.0),
            4, 4.0
        ),
        Specs(
            5, "engine5", "mileage5", Dimension(5.0, 5.0, 5.0),
            5, 5.0
        ),
    )

    override suspend fun fetchCar(id: Int): Car {
        return cars.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Car with ID $id not found.")
    }

    override suspend fun fetchSpecs(id: Int): Specs {
        return specs.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Specs with ID $id not found.")
    }

}

class FakeFetchExceptionRepo : CarRepository() {
    override suspend fun fetchCar(id: Int): Car {
        throw ApiService.FetchException("Fetch Exception")
    }

    override suspend fun fetchSpecs(id: Int): Specs {
        throw ApiService.FetchException("Fetch Exception")
    }
}

class FakeOtherExceptionRepo : CarRepository() {
    override suspend fun fetchCar(id: Int): Car {
        throw Exception("Other Exception")
    }

    override suspend fun fetchSpecs(id: Int): Specs {
        throw Exception("Other Exception")
    }
}