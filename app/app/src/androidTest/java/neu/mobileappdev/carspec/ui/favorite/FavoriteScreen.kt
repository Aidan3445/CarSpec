package neu.mobileappdev.carspec.ui.favorite

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import neu.mobileappdev.carspec.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteScreen {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        composeTestRule.onNodeWithTag("usernameField").performTextInput("admin")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("password")
        composeTestRule.onNodeWithTag("loginButton").performClick()

        composeTestRule.waitUntil {
            composeTestRule.onNodeWithTag("tab0").isDisplayed()
        }
    }

    @Test
    fun carCardsAreVisibleWhenFavoritesExist() {
        // Assuming `favViewModel` is populated with some cars
        composeTestRule.onNodeWithTag("carList").assertIsDisplayed()
        composeTestRule.onAllNodes(hasTestTag("CarCard")).assertCountEquals(2)  // Adjust count based on setup
    }

    @Test
    fun navigationOccursOnCarCardClick() {
        val carId = 1  // Assuming there's a car with ID 1
        composeTestRule.onNodeWithText("Car $carId").performClick()
        assertEquals("car/$carId", navController.currentBackStackEntry?.destination?.route)
    }

}
