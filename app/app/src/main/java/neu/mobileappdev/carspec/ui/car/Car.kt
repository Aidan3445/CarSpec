package neu.mobileappdev.carspec.ui.car

import android.app.Application
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neu.mobileappdev.carspec.ui.components.SpecsPopup

@Preview(showBackground = true)
@Composable
fun Car(
    navController: NavController = rememberNavController(),
    carID: Int = 1,
) {
    val appContext = LocalContext.current.applicationContext as Application
    val viewModel: CarViewModel = viewModel(
        factory = CarViewModelFactory(carID, appContext)
    )

    val car = viewModel.car.observeAsState().value
    val carName = car?.name ?: "Loading Car Information..."


    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        text = carName,
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )



//    SpecsPopup()
}
