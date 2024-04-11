package neu.mobileappdev.carspec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import neu.mobileappdev.carspec.api.CarQuery
import neu.mobileappdev.carspec.ui.car.Car
import neu.mobileappdev.carspec.ui.favorites.Favorites
import neu.mobileappdev.carspec.ui.home.Home
import neu.mobileappdev.carspec.ui.home.HomeViewModel
import neu.mobileappdev.carspec.ui.login.Login
import neu.mobileappdev.carspec.ui.navigation.Animations
import neu.mobileappdev.carspec.ui.navigation.NavGraph
import neu.mobileappdev.carspec.ui.navigation.NavMenu
import neu.mobileappdev.carspec.ui.navigation.NavMenuViewModel
import neu.mobileappdev.carspec.ui.search.Search

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Content()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Content() {
        val navController = rememberNavController()
        val navMenuViewModel = NavMenuViewModel()
        val homeViewModel = HomeViewModel(query = CarQuery(year = 2023))

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // App name
            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.purple_700))
                    .padding(0.dp, 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }

            // NavHost
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = NavGraph.LOGIN,
            ) {
                composable(
                    NavGraph.LOGIN,
                    exitTransition = { Animations.exitLeft },
                    ) {
                    Login(navController = navController)
                }
                composable(
                    NavGraph.HOME,
                    enterTransition = {
                        if (navController.previousBackStackEntry?.destination?.route == NavGraph.LOGIN)
                            Animations.enterRight
                        else
                            Animations.enterLeft },
                    exitTransition = { Animations.exitLeft },
                    ) {
                    Home(navController, homeViewModel)
                }
                composable(
                    NavGraph.FAVORITES,
                    enterTransition = {
                        if (navController.previousBackStackEntry?.destination?.route == NavGraph.HOME) {
                            Animations.enterRight
                        } else {
                            Animations.enterLeft
                        }
                    },
                    exitTransition = {
                        if (navBackStackEntry?.destination?.route == NavGraph.HOME) {
                            Animations.exitRight
                        } else {
                            Animations.exitLeft
                        }
                    }
                    ) {
                    Favorites()
                }
                composable(
                    NavGraph.SEARCH,
                    enterTransition = {
                      if (navController.previousBackStackEntry?.destination?.route == NavGraph.CAR) {
                          Animations.enterLeft
                      } else {
                          Animations.enterRight
                      }
                    },
                    exitTransition = { Animations.exitRight },
                    ) {
                    Search()
                }
                composable(
                    NavGraph.CAR,
                    arguments = listOf(navArgument("carID") {type = NavType.IntType}),
                    enterTransition = {
                        navMenuViewModel.setPageIndex(-1)
                        Animations.enterRight },
                    exitTransition = { Animations.exitRight },
                    ) {
                    Car()
                }
            }

            // Navigation menu
            if (navBackStackEntry?.destination?.route != NavGraph.LOGIN) {
                NavMenu(navController,  navMenuViewModel)
            }
        }
    }
}
