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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import neu.mobileappdev.carspec.ui.home.Home
import neu.mobileappdev.carspec.ui.navigation.NavGraph
import neu.mobileappdev.carspec.ui.navigation.NavMenu
import neu.mobileappdev.carspec.ui.navigation.NavMenuViewModel

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
                navController = navController,
                startDestination = NavGraph.login,
//                enterTransition = { Animations.enterRight },
//                exitTransition = { Animations.exitLeft },
//                popEnterTransition = { Animations.enterLeft },
//                popExitTransition = { Animations.exitRight },
            ) {
                composable(NavGraph.login) {
                    Text(text = "Login")
                    Button(onClick = {
                        navController.navigate(NavGraph.home)
                    }) {
                    }
                }
                composable(NavGraph.home) {
                    Home(navController)
                }
                composable(NavGraph.favorites) {
                    Text(text = "Favorites")
                }
                composable(NavGraph.search) {
                    Text(text = "Search")
                }
                composable(NavGraph.car) {
                    Text(text = "Car")
                }
            }

            // Navigation menu
            if (navBackStackEntry?.destination?.route != NavGraph.login) {
                NavMenu(navController,  navMenuViewModel)
            }
        }
    }
}
