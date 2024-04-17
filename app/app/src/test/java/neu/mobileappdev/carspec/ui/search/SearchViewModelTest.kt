package neu.mobileappdev.carspec.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel
    private lateinit var observer: Observer<String?>

    @Before
    fun setUp() {
        viewModel = SearchViewModel()
        observer = Observer { }
        viewModel.route.observeForever(observer)
    }

    @Test
    fun searchNameOnly() {
        // given
        val name = "name"

        // preconditions
        assert(viewModel.route.value == null)

        // when
        viewModel.search(name, "", "")

        // then
        assertEquals("page0?name=$name", viewModel.route.value)
    }

    @Test
    fun searchMakeOnly() {
        // given
        val make = "make"

        // preconditions
        assert(viewModel.route.value == null)

        // when
        viewModel.search("", make, "")

        // then
        assertEquals("page0?make=$make", viewModel.route.value)
    }

    @Test
    fun searchYearOnly() {
        // given
        val year = "year"

        // preconditions
        assert(viewModel.route.value == null)

        // when
        viewModel.search("", "", year)

        // then
        assertEquals("page0?year=$year", viewModel.route.value)
    }

    @Test
    fun searchAllFields() {
        // given
        val name = "name"
        val make = "make"
        val year = "year"

        // preconditions
        assert(viewModel.route.value == null)

        // when
        viewModel.search(name, make, year)

        // then
        assertEquals("page0?name=$name&make=$make&year=$year", viewModel.route.value)
    }

    @Test
    fun searchNoFields() {
        // preconditions
        assert(viewModel.route.value == null)

        // when
        viewModel.search("", "", "")

        // then
        viewModel.route.observeForever {
            assert(false)
        }
    }

    @Test
    fun searchNameAndMake() {
        // given
        val name = "name"
        val make = "make"

        // preconditions
        assert(viewModel.route.value == null)

        // when
        viewModel.search(name, make, "")

        // then
        assertEquals("page0?name=$name&make=$make", viewModel.route.value)
    }

    @Test
    fun searchNameAndYear() {
        // given
        val name = "name"
        val year = "year"

        // preconditions
        assert(viewModel.route.value == null)

        // when
        viewModel.search(name, "", year)

        // then
        assertEquals("page0?name=$name&year=$year", viewModel.route.value)
    }

    @Test
    fun searchMakeAndYear() {
        // given
        val make = "make"
        val year = "year"

        // preconditions
        assert(viewModel.route.value == null)

        // when
        viewModel.search("", make, year)

        // then
        assertEquals("page0?make=$make&year=$year", viewModel.route.value)
    }

    @Test
    fun searchNameAndMakeAndYear() {
        // given
        val name = "name"
        val make = "make"
        val year = "year"

        // preconditions
        assert(viewModel.route.value == null)

        // when
        viewModel.search(name, make, year)

        // then
        assertEquals("page0?name=$name&make=$make&year=$year", viewModel.route.value)
    }
}