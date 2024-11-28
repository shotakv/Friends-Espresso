package com.atiurin.sampleapp.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.ActivityTestRule
import com.atiurin.sampleapp.activity.UiElementsActivity
import com.atiurin.ultron.core.config.UltronConfig
import org.junit.BeforeClass
import org.junit.Rule

abstract class UiElementsTest : BaseTest() {

    @get:Rule val activityRule = ActivityScenarioRule(UiElementsActivity::class.java)


    companion object {
        @BeforeClass
        @JvmStatic
        fun speedUpAutomator() {
            UltronConfig.UiAutomator.speedUp()
        }
    }
}