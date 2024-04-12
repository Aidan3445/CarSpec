package neu.mobileappdev.carspec.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neu.mobileappdev.carspec.R
import neu.mobileappdev.carspec.ui.components.CarCard

@Preview(showBackground = true)
@Composable
fun Home(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = HomeViewModel()
) {
    // make view model

    // observe data
    val cars by viewModel.cars.observeAsState()
    val isFetching by viewModel.isFetching.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    Log.d("Home", "Cars: $cars")

    // effect only once
    LaunchedEffect(Unit) {
        // make initial fetch
        viewModel.fetchCars()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 10.dp),
                text = stringResource(id = R.string.title_home),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            if (viewModel.isFilterApplied()) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp, 0.dp),
                    onClick = { viewModel.clearQuery() }
                ) {
                    Text("Clear Filter")
                }
            }
        }

        if (isFetching == true) {
            // show loading spinner
            CircularProgressIndicator(
                modifier = Modifier.weight(1f)
            )
        } else if (errorMessage.isNullOrEmpty()) {
            // show car list
            LazyColumn {
                items(cars?.size ?: 0) { index ->
                    val car = cars!!.elementAt(index)
                    CarCard(car, index) {
                        navController.navigate("car/${car.id}")
                    }
                }
            }
        } else {
            // show error message and clear query button
            Text(
                text = errorMessage!!,
                color = Color.Gray,
                fontSize = 15.sp
            )
        }
    }
}
