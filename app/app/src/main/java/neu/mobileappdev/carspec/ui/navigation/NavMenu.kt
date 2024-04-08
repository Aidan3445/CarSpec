package neu.mobileappdev.carspec.ui.navigation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import neu.mobileappdev.carspec.R

@Preview(showBackground = true)
@Composable
fun NavMenu() {
    var tabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier =
            Modifier
                .requiredHeight(50.dp)
                .shadow(15.dp, ambientColor = Color.Black, spotColor = Color.Black),
    ) {
        TabRow(
            tabIndex,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(Color.White),
        ) {
            // Home tab
            Tab(
                selected = tabIndex == 0,
                onClick = {
                    Log.d("NavMenu", "Home button clicked")
                    tabIndex = 0
                },
                icon = {
                    Image(
                        painter = painterResource(R.drawable.ic_home_black_24dp),
                        contentDescription = "Home",
                    )
                },
            )

            // Favorites tab
            Tab(
                selected = tabIndex == 1,
                onClick = {
                    Log.d("NavMenu", "Favorites button clicked")
                    tabIndex = 1
                },
                icon = {
                    Image(
                        painter = painterResource(R.drawable.ic_favorites_black_24dp),
                        contentDescription = "Favorites",
                    )
                },
            )

            // Search tab
            Tab(
                selected = tabIndex == 2,
                onClick = {
                    Log.d("NavMenu", "Search button clicked")
                    tabIndex = 2
                },
                icon = {
                    Image(
                        painter = painterResource(R.drawable.ic_search_black_24dp),
                        contentDescription = "Search",
                    )
                },
            )
        }
    }
}
