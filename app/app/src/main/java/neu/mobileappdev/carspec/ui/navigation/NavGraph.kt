package neu.mobileappdev.carspec.ui.navigation

object NavGraph {
    const val LOGIN: String = "start"
    const val HOME: String = "page0?name={name}&make={make}&year={year}"
    const val FAVORITES: String = "page1"
    const val SEARCH: String = "page2"
    const val CAR: String = "car/{carID}"
}
