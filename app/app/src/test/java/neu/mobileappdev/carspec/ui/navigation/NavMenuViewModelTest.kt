package neu.mobileappdev.carspec.ui.navigation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NavMenuViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: NavMenuViewModel
    private lateinit var observer: Observer<Int>

    @Before
    fun setUp() {
        viewModel = NavMenuViewModel()
        observer = Observer { }
        viewModel.pageIndex.observeForever(observer)
    }

    @Test
    fun setPageIndex() {
        // given
        val index = 1

        // preconditions
        assert(viewModel.pageIndex.value == null)

        // when
        viewModel.setPageIndex(index)

        // then
        assertEquals(index, viewModel.pageIndex.value)
    }
}