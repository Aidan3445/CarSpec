package neu.mobileappdev.carspec.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import neu.mobileappdev.carspec.R

@Preview(showBackground = true)
@Composable
fun NavMenu(
    pageNumber: Number = 10,
    hasPrev: Boolean = true,
    jumpToPage1: Boolean = true,
    prev: () -> Unit = {},
    next: () -> Unit = {},
    toPage1: () -> Unit = {},
) {
    Column (
        modifier = Modifier
            .requiredHeight(50.dp)
            .shadow(15.dp, ambientColor = Color.Black, spotColor = Color.Black)
    ){
        //HorizontalDivider()

        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Home button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        prev()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Image(painter = painterResource(R.drawable.ic_home_black_24dp), contentDescription = "Home")
            }

            VerticalDivider()

            // Favorites button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        prev()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Image(painter = painterResource(R.drawable.ic_favorites_black_24dp), contentDescription = "Home")
            }

            VerticalDivider()

            // Search button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        prev()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Image(painter = painterResource(R.drawable.ic_search_black_24dp), contentDescription = "Home")
            }
        }
    }
}
