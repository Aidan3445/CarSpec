package neu.mobileappdev.carspec.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import neu.mobileappdev.carspec.api.Specs

@Composable
fun SpecsPopup(specs: Specs?, onDismissRequest: () -> Unit) {
    if (specs != null) {
        Dialog(onDismissRequest = onDismissRequest) {
            Surface(
                modifier = Modifier.padding(16.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Specifications", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Engine: ${specs.engine}", style = MaterialTheme.typography.bodyLarge)
                    Text("Mileage: ${specs.mileage}", style = MaterialTheme.typography.bodyLarge)
                    Text("Dimensions (inches): ${specs.dimensions.length} L x " +
                            "${specs.dimensions.width} W x ${specs.dimensions.height} H",
                        style = MaterialTheme.typography.bodyLarge)
                    Text("Horsepower: ${specs.horsepower}", style = MaterialTheme.typography.bodyLarge)
                    Text("Zero to Sixty: ${specs.zeroToSixty}", style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = onDismissRequest) {
                            Text("Exit")
                        }
                    }
                }
            }
        }
    }
}