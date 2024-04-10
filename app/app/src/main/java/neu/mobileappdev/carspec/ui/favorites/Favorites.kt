package neu.mobileappdev.carspec.ui.favorites

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neu.mobileappdev.carspec.R
import neu.mobileappdev.carspec.ui.navigation.NavMenuViewModel

val navIcons =
    listOf(
        R.drawable.ic_home_black_24dp,
        R.drawable.ic_favorites_black_24dp,
        R.drawable.ic_search_black_24dp,
    )

@Preview(showBackground = true)
@Composable
fun NavMenu(
    viewModel: NavMenuViewModel = NavMenuViewModel(),
    navController: NavController = rememberNavController(),
) {
    val tabIndex by viewModel.pageIndex.observeAsState(0)

    Column(
        modifier =
            Modifier
                .requiredHeight(50.dp)
                .shadow(15.dp, spotColor = Color.Black, clip = false),
    ) {
        TabRow(
            tabIndex,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(Color.White),
            indicator = { tabPositions ->
                val tabPosition = tabPositions[tabIndex]
                SecondaryIndicator(
                    modifier =
                        Modifier
                            .tabIndicatorOffset(tabPosition)
                            .fillMaxSize()
                            .shadow(
                                15.dp,
                                ambientColor = colorResource(R.color.selected_tab),
                                spotColor = colorResource(R.color.black),
                                clip = false,
                            ),
                    color = colorResource(id = R.color.selected_tab),
                )
            },
        ) {
            navIcons.forEachIndexed { index, resource ->
                Tab(
                    icon = {
                        Image(
                            painter = painterResource(id = resource),
                            contentDescription = null,
                        )
                    },
                    selected = tabIndex == index,
                    selectedContentColor = colorResource(id = R.color.selected_tab),
                    onClick = {
                        Log.d("NavMenu", "Tab index: page$index")
                        viewModel.setPageIndex(index)
                        navController.navigate("page$index")
                    },
                )
            }
        }
    }
}
