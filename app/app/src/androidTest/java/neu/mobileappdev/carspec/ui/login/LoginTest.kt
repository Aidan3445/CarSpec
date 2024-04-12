package neu.mobileappdev.carspec.ui.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import neu.mobileappdev.carspec.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testLoginDisplayed() {
        composeTestRule.onNodeWithTag("loginButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("usernameField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("passwordField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("hintLink").assertIsDisplayed()
    }

    @Test
    fun testLoginHint() {
        composeTestRule.onNodeWithTag("hintLink").performClick()

        composeTestRule.waitUntil(25000) {
            composeTestRule.onNodeWithTag("snackbarHost").isDisplayed()
            composeTestRule.onNodeWithText("Username: admin\nPassword: password").isDisplayed()

        }

        composeTestRule.onNodeWithText("Username: admin\nPassword: password").assertIsDisplayed()
    }

    @Test
    fun testLoginInvalid() {
        composeTestRule.onNodeWithTag("loginButton").performClick()

        composeTestRule.waitUntil(25000) {
            composeTestRule.onNodeWithTag("snackbarHost").isDisplayed()
            composeTestRule.onNodeWithText("Invalid username or password").isDisplayed()

        }

        composeTestRule.onNodeWithText("Invalid username or password").assertIsDisplayed()
    }

    @Test
    fun testLoginSuccess() {
        composeTestRule.onNodeWithTag("usernameField").performTextInput("admin")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("password")
        composeTestRule.onNodeWithTag("loginButton").performClick()

        composeTestRule.waitUntil(25000) {
            composeTestRule.onNodeWithText("Home").isDisplayed()
        }

        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
    }
}