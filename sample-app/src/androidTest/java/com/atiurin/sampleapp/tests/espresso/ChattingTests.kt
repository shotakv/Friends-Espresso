package com.atiurin.sampleapp.tests.espresso

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.atiurin.sampleapp.activity.MainActivity
import com.atiurin.sampleapp.constants.Constants
import com.atiurin.sampleapp.helper.isTextOnScreen
import com.atiurin.sampleapp.helper.isViewDisplayed
import com.atiurin.sampleapp.helper.typeText
import com.atiurin.sampleapp.pages.UIElementPage
import com.atiurin.sampleapp.steps.UIElementPageSteps
import com.atiurin.sampleapp.tests.BaseTest
import com.atiurin.ultron.extensions.tap
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ChattingTests : BaseTest() {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun textWithMyFriend() {
        with(UIElementPageSteps) {
            checkDashboardLoaded()

            openChatWithFriend(Constants.FRIEND_NAME)

            sendMessageInTheChat(Constants.MSG_TEXT)

        }
    }

    @Test
    fun allCornerCirclesMarked() {
        with(UIElementPageSteps) {
            checkDashboardLoaded()

            toggleBurgerManu()

            navigateCustomClicks()

            validateCirclesPage()

            tapCornerCircles()

            validateCornerCircles()
        }
    }
}