package neu.mobileappdev.carspec.ui.car

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neu.mobileappdev.carspec.ui.components.SpecsPopup

@Preview(showBackground = true)
@Composable
fun Car(
    navController: NavController = rememberNavController(),
    carID: Int = 1
) {
    val viewModel = CarViewModel(carID)

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        text = "Car Name $carID",
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )

    SpecsPopup()
}
