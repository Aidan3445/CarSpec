package neu.mobileappdev.carspec.ui.favorites

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neu.mobileappdev.carspec.R
import neu.mobileappdev.carspec.ui.components.CarCard

@Composable
fun Favorites(
    application: Application,
    navController: NavController = rememberNavController(),
    favViewModel: FavoritesViewModel = FavoritesViewModel(application)
) {
    val favoriteCars by favViewModel.favCars.observeAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier.testTag("carList")) {
        itemsIndexed(favoriteCars) { index, car ->
            CarCard(
                car = car,
                onClick = {
                 navController.navigate("car/${car.id}")
                }
            )

        }
        item {
            if (favoriteCars.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_favorites),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
    }
}
