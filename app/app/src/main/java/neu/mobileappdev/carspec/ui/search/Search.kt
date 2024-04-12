package neu.mobileappdev.carspec.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neu.mobileappdev.carspec.R

@Preview(showBackground = true)
@Composable
fun Search(
    navController: NavController = rememberNavController(),
    viewModel: SearchViewModel = SearchViewModel(),
) {
    // watch query
    val route = viewModel.route.observeAsState()

    // search fields
    var name by remember { mutableStateOf("")}
    var make by remember { mutableStateOf("")}
    var year by remember { mutableStateOf("")}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // screen title
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
            text = stringResource(id = R.string.title_search),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)

        // search inputs
        Column(
            modifier = Modifier.padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
            TextField(value = make, onValueChange = { make = it }, label = { Text("Make") })
            TextField(value = year, onValueChange = { year = it }, label = { Text("Model") })
        }

        // search button
        Button(onClick = {
            // try search with query
            viewModel.search(name, make, year)
        }) {
            Text(text = "Search")
        }

        // navigate to search results
        LaunchedEffect(route.value) {
            route.value?.let {
                navController.navigate(route.value!!)
            }
        }
    }

}
