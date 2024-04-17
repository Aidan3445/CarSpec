package neu.mobileappdev.carspec.ui.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import neu.mobileappdev.carspec.Database.FakeFavoriteViewModel
import neu.mobileappdev.carspec.api.Car
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FakeFavoriteViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FakeFavoriteViewModel
    private val car1 = Car(id = 1, make = "Toyota", year = 2020)
    private val car2 = Car(id = 2, make = "Honda", year = 2021)

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = FakeFavoriteViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when toggleFavorite is called with non-favorite car, it should add it to favorites`() =
        runTest {
            viewModel.toggleFavorite(car1)
            val observer = Observer<Set<Int>> {}
            try {
                viewModel.favoriteCars.observeForever(observer)
                assertTrue(viewModel.favoriteCars.value!!.contains(car1.id))
            } finally {
                viewModel.favoriteCars.removeObserver(observer)
            }
        }

    @Test
    fun `when toggleFavorite is called with favorite car, it should remove it from favorites`() =
        runTest {
            viewModel.toggleFavorite(car1)  // First toggle to add
            viewModel.toggleFavorite(car1)  // Second toggle to remove
            val observer = Observer<Set<Int>> {}
            try {
                viewModel.favoriteCars.observeForever(observer)
                assertFalse(viewModel.favoriteCars.value!!.contains(car1.id))
            } finally {
                viewModel.favoriteCars.removeObserver(observer)
            }
        }

    @Test
    fun `favCars LiveData should be correctly populated on getAllFavorites`() = runTest {
        val observer = Observer<List<Car>> {}
        try {
            viewModel.favCars.observeForever(observer)
            viewModel.toggleFavorite(car1)
            viewModel.toggleFavorite(car2)
            viewModel.getAllFavorites()
            assertTrue(viewModel.favCars.value?.containsAll(listOf(car1, car2)) ?: false)
            viewModel.toggleFavorite(car1)
            viewModel.getAllFavorites()
            assertFalse(viewModel.favCars.value?.containsAll(listOf(car1)) ?: false)
        } finally {
            viewModel.favCars.removeObserver(observer)
        }
    }

    @Test
    fun `favCars LiveData should update when a car is toggled from favorite`() = runTest {
        viewModel.toggleFavorite(car1)
        val observer = Observer<List<Car>> {}
        try {
            viewModel.favCars.observeForever(observer)
            viewModel.getAllFavorites()
            assertTrue(viewModel.favCars.value!!.any { it.id == car1.id })
            viewModel.toggleFavorite(car1)
            viewModel.getAllFavorites()
            assertFalse(viewModel.favCars.value!!.any { it.id == car1.id })
        } finally {
            viewModel.favCars.removeObserver(observer)
        }
    }

}
