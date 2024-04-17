package neu.mobileappdev.carspec.ui.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neu.mobileappdev.carspec.ui.navigation.NavGraph

@Preview(showBackground = true)
@Composable
fun Login(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = LoginViewModel(),
) {
    // get error and success updates from the view model
    val errorMessage by viewModel.message.observeAsState()
    val loginSuccess by viewModel.loginSuccess.observeAsState()

    // error message notification
    val state = remember { SnackbarHostState() }
    SnackbarHost(
        hostState = remember { state },
        modifier = Modifier
            .fillMaxWidth()
            .testTag("snackbarHost"),
    )

    // use column to stack the username, password, and login button
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // remember fields
        var username by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // username field
            TextField(
                singleLine = true,
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.testTag("usernameField"),
            )

            // password field
            TextField(
                singleLine = true,
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.testTag("passwordField"),
            )

            // forgot login hint
            Text(
                text = "Forgot login? Click for a hint.",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable { viewModel.getHint() }
                    .testTag("hintLink"),
            )
        }

        // login button
        Button(
            onClick = {
                Log.d("LoginButton", "B: $username, $password")
                viewModel.tryLogin(username, password)
            },
            modifier = Modifier
                .fillMaxWidth(.9f)
                .testTag("loginButton"),
        ) {
            Text(text = "Login")
        }

        // handle login attempts
        LaunchedEffect(loginSuccess) {
            loginSuccess?.let {
                // navigate to the home fragment
                navController.navigate(NavGraph.HOME)
                // clear the username and password fields
                username = ""
                password = ""
            }
        }

        LaunchedEffect(errorMessage) {
            if (errorMessage.isNullOrEmpty()) return@LaunchedEffect

            state.showSnackbar(errorMessage!!)
            viewModel.clearErrorMessage()
        }
    }
}
