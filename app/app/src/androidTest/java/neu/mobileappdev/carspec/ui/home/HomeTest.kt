package neu.mobileappdev.carspec.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
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
class HomeTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.onNodeWithTag("usernameField").performTextInput("admin")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("password")
        composeTestRule.onNodeWithTag("loginButton").performClick()

        composeTestRule.waitUntil(25000) {
            composeTestRule.onNodeWithTag("tab0").isDisplayed()
        }

        composeTestRule.onNodeWithTag("tab0").performClick()
    }

    @Test
    fun testHomeDisplayed() {
        composeTestRule.onNodeWithTag(("homeTitle")).assertIsDisplayed()
        composeTestRule.onNodeWithTag("clearFilterButton").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("carList").assertIsDisplayed()
        composeTestRule.onNodeWithTag("errorMessage").assertIsNotDisplayed()
    }

    @Test
    fun testFilterAndClear() {
        composeTestRule.onNodeWithTag("tab2").performClick()

        Thread.sleep(3000)

        composeTestRule.waitUntil(25000) {
            composeTestRule.onNodeWithTag("searchTitle").isDisplayed()
        }

        composeTestRule.onNodeWithTag("yearField").performTextInput("9999")
        composeTestRule.onNodeWithTag("searchButton").performClick()

        composeTestRule.waitUntil(25000) {
            composeTestRule.onNodeWithTag("homeTitle").isDisplayed()
        }

        composeTestRule.onNodeWithTag("clearFilterButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("errorMessage").assertIsDisplayed()

        composeTestRule.onNodeWithTag("clearFilterButton").performClick()

        composeTestRule.waitUntil(25000) {
            composeTestRule.onNodeWithTag("clearFilterButton").isNotDisplayed()
        }

        composeTestRule.onNodeWithTag("clearFilterButton").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("errorMessage").assertIsNotDisplayed()
    }
}
