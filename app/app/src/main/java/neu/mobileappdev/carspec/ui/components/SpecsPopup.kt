package neu.mobileappdev.carspec.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import neu.mobileappdev.carspec.R
import neu.mobileappdev.carspec.api.Specs

@Preview(showBackground = true)
@Composable
fun SpecsPopup(specs: Specs = Specs()) {
    Column(
        modifier = Modifier.run {
            padding(30.dp, 100.dp)
                .fillMaxSize()
                .background(color = colorResource(id = R.color.pop_up_bg))
        }
    ) {
        Text(text = "SPECS")
    }
}
