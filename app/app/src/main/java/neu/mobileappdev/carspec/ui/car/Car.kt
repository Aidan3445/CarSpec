package neu.mobileappdev.carspec.ui.car

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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

    LaunchedEffect(Unit) {
        viewModel.fetchCar()
        viewModel.fetchSpecs()
    }

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
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("Model")
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "Make: $carMake",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("Make")
        )

        Text(
            text = "Year: $carYear",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("Year")
        )

        Spacer(modifier = Modifier.height(16.dp))

        car?.image?.let { imageUrl ->
            GlideImage(
                model = imageUrl,
                contentDescription = "Car Image",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .testTag("carImage"),
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

            IconButton(
                onClick = {
                    if (car != null) {
                        favoritesViewModel.toggleFavorite(car)
                    }
                },
                modifier = Modifier.testTag("favButton")
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isFavorite)
                            R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline
                    ),
                    contentDescription = if (isFavorite) "Remove from Favorites"
                    else "Add to Favorites",
                    modifier = Modifier.testTag("favIcon")
                )
            }
        }
        if (showSpecsPopup) {
            SpecsPopup(
                specs = specs,
                onDismissRequest = { showSpecsPopup = false },
                modifier = Modifier.testTag("specsPopup")
            )
        }
    }
}
