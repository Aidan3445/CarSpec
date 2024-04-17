package neu.mobileappdev.carspec.ui.favorites

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // screen title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .testTag("searchTitle"),
            text = stringResource(id = R.string.title_favorites),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        LazyColumn(modifier = Modifier.testTag("carList")) {
            itemsIndexed(favoriteCars) { index, car ->
                CarCard(
                    car = car,
                    index = index,
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
}
