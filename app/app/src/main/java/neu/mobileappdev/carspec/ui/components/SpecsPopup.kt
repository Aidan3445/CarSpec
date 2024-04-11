package neu.mobileappdev.carspec.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import neu.mobileappdev.carspec.api.Specs

@Preview(showBackground = true)
@Composable
fun SpecsPopup(specs: Specs = Specs()) {
    Text(text = "SpecsPopup")
    // TODO
}
