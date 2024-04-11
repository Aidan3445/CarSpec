package neu.mobileappdev.carspec.ui.cars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neu.mobileappdev.carspec.ui.components.CarCard

@Preview(showBackground = true)
@Composable
fun Home(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = HomeViewModel(),
    ) {
    // observe data
    val cars by viewModel.cars.observeAsState()
    val isFetching by viewModel.isFetching.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    // effect only once
    LaunchedEffect(Unit) {
        // make initial fetch
        viewModel.fetchCars()
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Cars",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        if (isFetching == true) {
            // show loading spinner
            CircularProgressIndicator()
        } else if (errorMessage.isNullOrEmpty()) {
            // show car list
            LazyColumn {
                items(cars?.size ?: 0) { index ->
                    CarCard(cars!!.elementAt(index))
                }
            }
        } else {
            // show error message and clear query button
            Text(
                text = errorMessage!!,
                color = Color.Gray,
                fontSize = 15.sp
            )

            Button(onClick = { viewModel.clearQuery() }) {
                Text("Clear Query")
            }
        }

    }
}
