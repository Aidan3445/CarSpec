package neu.mobileappdev.carspec.ui.car

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import neu.mobileappdev.carspec.R
import neu.mobileappdev.carspec.ui.components.SpecsPopup
import neu.mobileappdev.carspec.ui.favorites.FavoritesViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun Car(
    navController: NavController = rememberNavController(),
    carID: Int = 1,
) {
    val appContext = LocalContext.current.applicationContext as Application
    val viewModel: CarViewModel = viewModel(
        factory = CarViewModelFactory(carID)
    )
    val favoritesViewModel = FavoritesViewModel(appContext)

    val car = viewModel.car.observeAsState().value
    val carName = car?.name ?: "Loading..."
    val carMake = car?.make ?: "Loading..."
    val carYear = car?.year?.toString() ?: "Loading..."

    var showSpecsPopup by remember { mutableStateOf(false) }
    val favList = favoritesViewModel.favoriteCars.observeAsState(setOf())
    val isFavorite = car?.id?.let { it in favList.value } ?: false
    val specs = viewModel.carSpecs.observeAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Model: $carName",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "Make: $carMake",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Year: $carYear",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        car?.image?.let { imageUrl ->
            GlideImage(
                model = imageUrl,
                contentDescription = "Car Image",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit,
                loading = placeholder(R.drawable.ic_car_black),
                failure = placeholder(R.drawable.ic_car_black)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { showSpecsPopup = true },
                modifier = Modifier
            ) {
                Text(text = "More Specs")
            }

            IconButton(onClick = {
                if (car != null) {
                    favoritesViewModel.toggleFavorite(car)
                }
            }) {
                Icon(
                    painter = painterResource(id = if (isFavorite)
                        R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline),
                    contentDescription = if (isFavorite) "Remove from Favorites"
                    else "Add to Favorites"
                )
            }
        }
        if (showSpecsPopup) {
            SpecsPopup(specs = specs, onDismissRequest = { showSpecsPopup = false })
        }
    }
}
