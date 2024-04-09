package neu.mobileappdev.carspec.ui.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavMenuViewModel : ViewModel() {
    private val pageData = MutableLiveData<Int>()
    val pageIndex get() = pageData

    fun setPageIndex(index: Int) {
        pageData.value = index
    }
}
