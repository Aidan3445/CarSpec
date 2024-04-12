package neu.mobileappdev.carspec.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import neu.mobileappdev.carspec.R
import neu.mobileappdev.carspec.api.Car

@Preview(showBackground = true)
@Composable
fun CarCard(
    car: Car =
        Car(
            1,
            "Corolla",
            "Toyota",
            2021,
            "https://www.cars.com/i/large/in/v2/stock_photos/a1bd413b-0726-49b1-929b-53f83760953a/3f618153-c2c3-41e2-9684-5493885a6d53.png",
        ),
    index: Int = 0,
    onClick: () -> Unit = { Log.d("CarCard", "onClick") },
) {
    Row(
        modifier =
        Modifier
            .requiredHeight(100.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                colorResource(
                    id =
                    if (index % 2 == 0) R.color.purple_200 else R.color.purple_250
                )
            )
            .padding(10.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // car image
        Box(
            modifier =
            Modifier
                .requiredHeight(80.dp)
                .requiredWidth(80.dp)
                .background(colorResource(id = R.color.purple_500)),
        ) {
            // TODO add image
        }

        // car details
        Text(
            text = "${car.year} ${car.make} ${car.name}",
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(25.dp, 0.dp, 10.dp, 0.dp)
                .fillMaxWidth()
        )
    }
}
