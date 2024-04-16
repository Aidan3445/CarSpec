package neu.mobileappdev.carspec.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import neu.mobileappdev.carspec.R
import neu.mobileappdev.carspec.api.Car

@OptIn(ExperimentalGlideComposeApi::class)
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
    onClick: () -> Unit = { Log.d("CarCard", "onClick") }
) {
    Row(
        modifier = Modifier
            .testTag("carCard_$index")
            .height(100.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .background(colorResource(id = if (index % 2 == 0) R.color.purple_200 else R.color.purple_250))
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // car image
        Box(
            modifier = Modifier
                .requiredHeight(80.dp)
                .requiredWidth(80.dp)
                .background(Color.White)
        ) {
            GlideImage(
                model = car.image,
                contentDescription = "Car Image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit,
                loading = placeholder(R.drawable.ic_car_black),
                failure = placeholder(R.drawable.ic_car_black)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // car details
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${car.year} ${car.make} ${car.name}",
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }

}
