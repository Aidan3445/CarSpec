package neu.mobileappdev.carspec.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    // live data for the query filter to pass to the API
    private val routeData = MutableLiveData<String>(null)
    val route get() = routeData

    // set the query filter if at least one field is not empty
    fun search(name: String, make: String, year: String) {
        var route = "page0"
        if (name.isNotEmpty() || make.isNotEmpty() || year.isNotEmpty()) {
            route += "?"
            if (name.isNotEmpty()) {
                route += "name=$name"
            }
            if (make.isNotEmpty()) {
                if (route.endsWith("?").not()) {
                    route += "&"
                }
                route += "make=$make"
            }
            if (year.isNotEmpty()) {
                if (route.endsWith("?").not()) {
                    route += "&"
                }
                route += "year=$year"
            }

            Log.d("SearchViewModel", "search: $route")

            // post the route data to the live data
            routeData.postValue(route)
        }
    }
}