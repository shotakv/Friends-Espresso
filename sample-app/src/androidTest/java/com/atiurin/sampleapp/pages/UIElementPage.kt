package com.atiurin.sampleapp.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.sampleapp.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.instanceOf

object UIElementPage {

    val burgerManubtn: Matcher<View> by lazy { withContentDescription("Open navigation drawer") }

    val textInput: Matcher<View> by lazy { Matchers.allOf(withId(R.id.message_input_text)) }
    val sendButton: Matcher<View> by lazy { withId(R.id.send_button) }
    val customClicksElement: Matcher<View> by lazy { withId(R.id.custom_clicks_nav_item) }

    val topLeftCircle: Matcher<View> by lazy { withId(R.id.rB_top_left) }
    val bottomLeftCircle: Matcher<View> by lazy { withId(R.id.rB_bottom_left) }
    val topRightCircle: Matcher<View> by lazy { withId(R.id.rB_top_right) }
    val bottomRightCircle: Matcher<View> by lazy { withId(R.id.rB_bottom_right) }

    val circlesContainer: Matcher<View> by lazy { withId(R.id.imageView) }


    fun getFriendByName(friendName: String): Matcher<View> {
        return Matchers.allOf(
            withId(R.id.tv_name),
            withText(friendName)
        )
    }

    fun getAddresseeName(friendName: String): Matcher<View> {
        return Matchers.allOf(
            withId(R.id.toolbar_title),
            withText(friendName)
        )
    }

    fun getMessageByText(msg: String): Matcher<View> {
        return Matchers.allOf(
            withId(R.id.message_text),
            withText(msg)
        )
    }

}