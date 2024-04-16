package neu.mobileappdev.carspec.ui.car

import androidx.compose.ui.test.assertIsDisplayed
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
class CarTest {
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

        composeTestRule.onNodeWithTag("tab0").performClick()

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("carList").assertIsDisplayed().fetchSemanticsNode().children.isNotEmpty()
        }

        composeTestRule.onNodeWithTag("carList").performScrollToIndex(0)
        composeTestRule.onNodeWithTag("carCard_0").performClick()

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("Model").isDisplayed()
        }
    }

    @Test
    fun carInfoIsDisplayed() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithTag("carImage").isDisplayed()
        }

        composeTestRule.onNodeWithTag("Model").assertExists()
        composeTestRule.onNodeWithTag("Make").assertExists()
        composeTestRule.onNodeWithTag("Year").assertExists()


        composeTestRule.onNodeWithTag("carImage").assertIsDisplayed()
        composeTestRule.onNodeWithTag("favButton").assertIsDisplayed()
    }

    @Test
    fun specsPopupDisplaysCorrectly() {
        composeTestRule.onNodeWithText("More Specs").performClick()
        composeTestRule.onNodeWithTag("specsPopup").assertIsDisplayed()

        composeTestRule.onNodeWithText("Specifications").assertExists()
        composeTestRule.onNodeWithTag("Engine").assertExists()
        composeTestRule.onNodeWithTag("Mileage").assertExists()
        composeTestRule.onNodeWithTag("Dimensions").assertExists()
        composeTestRule.onNodeWithTag("Horsepower").assertExists()
        composeTestRule.onNodeWithTag("ZeroToSixty").assertExists()
    }

    @Test
    fun closeSpecsPopupWorks() {
        composeTestRule.onNodeWithText("More Specs").performClick()

        composeTestRule.onNodeWithText("Exit").performClick()

        composeTestRule.onNodeWithTag("specsPopup").assertDoesNotExist()
    }
}