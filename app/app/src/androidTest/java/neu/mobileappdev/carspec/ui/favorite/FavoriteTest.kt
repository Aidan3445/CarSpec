package neu.mobileappdev.carspec.ui.favorite

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import neu.mobileappdev.carspec.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.onNodeWithTag("usernameField").performTextInput("admin")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("password")
        composeTestRule.onNodeWithTag("loginButton").performClick()

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("tab0").isDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testNoFavoritesAndExistingFavorites() {
        // PART 1
        // SETUP FOR NO FAVORITES - Navigate to favorites as no favorites exist
        composeTestRule.onNodeWithTag("tab1").performClick()

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("tab1").isDisplayed()
        }

        // For animation
        Thread.sleep(3000)

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("No Favorite Cars Yet!").isDisplayed()
        }

        // Checks that text "No Favorite Cars Yet!" is displayed
        composeTestRule.onNodeWithText("No Favorite Cars Yet!").assertIsDisplayed()

        // Checks that no carList is displayed
        composeTestRule.onNodeWithTag("carCard_0").assertIsNotDisplayed()

        Thread.sleep(3000)

        // PART 2

        // SETUP FOR EXISTING FAVORITES - Favorite the first car and navigate to favorites
        composeTestRule.onNodeWithTag("tab0").performClick()

        Thread.sleep(3000)

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("carList").isDisplayed()
        }

        composeTestRule.onNodeWithTag("carList").performScrollToIndex(0)
        composeTestRule.onNodeWithTag("carCard_0").performClick()

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("favButton").isDisplayed()
        }

        composeTestRule.onNodeWithTag("favButton").assertIsDisplayed()

        composeTestRule.waitForIdle()

        // For animation
        Thread.sleep(3000)

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("favIcon", useUnmergedTree = true).isDisplayed()
        }

        composeTestRule.onNodeWithTag("favIcon", useUnmergedTree = true)
            .assertContentDescriptionEquals("Add to Favorites")

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("favButton", useUnmergedTree = true).performClick()

        composeTestRule.waitForIdle()

        composeTestRule.waitUntilDoesNotExist(hasContentDescription("Add to Favorites"), 5000)

        composeTestRule.onNodeWithTag("tab1").performClick()

        // For animation
        Thread.sleep(3000)

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("carList").isDisplayed()
        }

        // Check if the carList has carCard tag of carCard_0
        composeTestRule.onNodeWithTag("carList").assertIsDisplayed()
        composeTestRule.waitUntil(10000) {
            composeTestRule.onNodeWithTag("carCard_0").isDisplayed()
        }

        // Checks that text "No Favorite Cars Yet!" is not displayed
        composeTestRule.onNodeWithText("No Favorite Cars Yet!").assertIsNotDisplayed()

        // Checks if the carCard has a CarInfo text tag
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("carInformation", useUnmergedTree = true).isDisplayed()
        }

        // Checks if the carCard has an image tag of carCardImage
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("carCardImage", useUnmergedTree = true).isDisplayed()
        }
    }

}
