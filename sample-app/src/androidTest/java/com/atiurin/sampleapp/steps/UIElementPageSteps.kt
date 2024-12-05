package com.atiurin.sampleapp.steps

import com.atiurin.sampleapp.constants.Constants
import com.atiurin.sampleapp.helper.isTextOnScreen
import com.atiurin.sampleapp.helper.isViewDisplayed
import com.atiurin.sampleapp.pages.UIElementPage


import com.atiurin.sampleapp.helper.typeText
import com.atiurin.sampleapp.pages.UIElementPage.burgerManubtn
import com.atiurin.sampleapp.pages.UIElementPage.customClicksElement
import com.atiurin.ultron.extensions.isChecked

import com.atiurin.ultron.extensions.tap

object UIElementPageSteps {
    fun checkDashboardLoaded(){
        isTextOnScreen(Constants.DASHBOARD_TEXT)
    }

    fun openChatWithFriend(name:String){
        with(UIElementPage){
            getFriendByName(name).isViewDisplayed()
            getFriendByName(name).tap()
            getAddresseeName(name).isViewDisplayed()
        }
    }

    fun sendMessageInTheChat(msg:String){
        with(UIElementPage){
            textInput.typeText(msg)
            sendButton.tap()
            getMessageByText(msg).isViewDisplayed()

        }
    }

    fun tapCornerCircles(){
        with(UIElementPage){
            bottomLeftCircle.tap()
            topLeftCircle.tap()
            bottomRightCircle.tap()
            topRightCircle.tap()
        }
    }

    fun validateCornerCircles(){
        with(UIElementPage){
            bottomLeftCircle.isChecked()
            topLeftCircle.isChecked()
            bottomLeftCircle.isChecked()
            topLeftCircle.isChecked()
        }
    }

    fun toggleBurgerManu(){
        with(UIElementPage){
            burgerManubtn.isViewDisplayed()
            burgerManubtn.tap()
        }
    }

    fun navigateCustomClicks(){
        with(UIElementPage){
            customClicksElement.isViewDisplayed()
            customClicksElement.tap()
        }
    }

    fun validateCirclesPage(){
        with(UIElementPage){
            circlesContainer.isViewDisplayed()
        }
    }



}