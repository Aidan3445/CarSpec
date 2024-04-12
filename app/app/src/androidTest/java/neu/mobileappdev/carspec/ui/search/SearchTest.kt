package neu.mobileappdev.carspec.ui.search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import neu.mobileappdev.carspec.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.onNodeWithTag("usernameField").performTextInput("admin")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("password")
        composeTestRule.onNodeWithTag("loginButton").performClick()

        composeTestRule.waitUntil(25000) {
            composeTestRule.onNodeWithTag("tab2").isDisplayed()
        }

        composeTestRule.onNodeWithTag("tab2").performClick()
    }

    @Test
    fun testSearchDisplayed() {
        composeTestRule.onNodeWithTag(("searchTitle")).assertIsDisplayed()
        composeTestRule.onNodeWithTag("nameField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("makeField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("yearField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("searchButton").assertIsDisplayed()
    }

    @Test
    fun testSearch() {
        composeTestRule.onNodeWithTag("nameField").performTextInput("Camry")
        composeTestRule.onNodeWithTag("searchButton").performClick()

        composeTestRule.waitUntil(25000) {
            composeTestRule.onNodeWithTag("homeTitle").isDisplayed()
        }

        composeTestRule.onNodeWithTag("homeTitle").assertIsDisplayed()
    }
}
