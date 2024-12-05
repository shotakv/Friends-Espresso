# Ultron

[ ![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.atiurin/ultron/badge.svg) ](https://maven-badges.herokuapp.com/maven-central/com.atiurin/ultron)
![Android CI](https://github.com/open-tool/ultron/workflows/AndroidCI/badge.svg)

Ultron is an easiest framework to develop Android UI tests. It makes your tests simple, stable and supportable.
It's based on Espresso, UI Automator and Compose UI testing framework. Ultron provides a lot of new great features.
Ultron also gives you a full control under your tests!

Moreover, you don't need to learn any new classes or special syntax. All magic actions and assertions are provided from crunch.
Ultron can be easially customised and extended. Wish you only stable tests!

![logo](https://user-images.githubusercontent.com/12834123/112367915-96321580-8ceb-11eb-90f6-ed44b5b53ab0.png)

## What are the benefits of using the framework?

- Simple and presentative syntax
- Stability of all actions and assertions
- Full control under any action or assertion
- An architectural approach to UI tests development
- Amazing mechanism of setups and teardowns (You even can setup preconditions for single test in test class. It won't affect the others)
***
### Attention! Ultron 2.x.x supports compose UI testing!
***
### A few words about syntax

The standard Google syntax is complex and not intuitive to understand. This is especially evident when interacting with the RecyclerView

Let's look at some examples:

#### 1. Simple compose operation (read wiki [here](https://github.com/open-tool/ultron/wiki/Compose#ultron-compose))

_Compose framework_

```kotlin
composeTestRule.onNode(hasTestTag("Continue")).performClick()
composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
```
_Ultron_

```kotlin
hasTestTag("Continue").click()
hasText("Welcome").assertIsDisplayed()
```

#### 2. Compose list operation (read wiki [here](https://github.com/open-tool/ultron/wiki/Compose#ultron-compose-lazycolumnlazyrow))

_Compose framework_

```kotlin
val itemMatcher = hasText(contact.name)
composeRule
    .onNodeWithTag(contactsListTestTag)
    .performScrollToNode(itemMatcher)
    .onChildren()
    .filterToOne(itemMatcher)
    .assertTextContains(contact.name)
```

_Ultron_

```kotlin
composeList(hasTestTag(contactsListTestTag))
    .item(hasText(contact.name))
    .assertTextContains(contact.name)
```
#### 3. Simple Espresso assertion and action.

_Espresso_

```kotlin
onView(withId(R.id.send_button)).check(isDisplayed()).perform(click())
```
_Ultron_

```kotlin
withId(R.id.send_button).isDisplayed().click()
```
It looks better. Names of all Ultron operations are the same as Espresso. It also provide a list of additional operations.

See [wiki](https://github.com/open-tool/ultron/wiki/Espresso-operations) for more info.

#### 4. Action on RecyclerView list item

_Espresso_

```kotlin
onView(withId(R.id.recycler_friends))
    .perform(
        RecyclerViewActions
            .scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText("Janice")))
    )
    .perform(
        RecyclerViewActions
            .actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Janice")),
                click()
            )
        )
```
_Ultron_

```kotlin
withRecyclerView(R.id.recycler_friends)
    .item(hasDescendant(withText("Janice")))
    .click()
```

Read [wiki](https://github.com/open-tool/ultron/wiki/RecyclerView) and realise the magic of how *Ultron* interacts with RecyclerView.

#### 5. Espresso WebView operations 

_Espresso_

```kotlin
onWebView()
    .withElement(findElement(Locator.ID, "text_input"))
    .perform(webKeys(newTitle))
    .withElement(findElement(Locator.ID, "button1"))
    .perform(webClick())
    .withElement(findElement(Locator.ID, "title"))
    .check(webMatches(getText(), containsString(newTitle)))
```

_Ultron_

```kotlin
id("text_input").webKeys(newTitle)
id("button1").webClick()
id("title").hasText(newTitle)
```

Read [wiki](https://github.com/open-tool/ultron/wiki/WebView) 

#### 6. UI Automator operations

_UI Automator_

```kotlin
val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
device
    .findObject(By.res("com.atiurin.sampleapp:id", "button1"))
    .click()
```

_Ultron_

```kotlin
byResId(R.id.button1).click() 
```
Read [wiki](https://github.com/open-tool/ultron/wiki/UI-Automator-operation) 
***
### You can get the result of any operation as boolean value

```kotlin
val isButtonDisplayed = withId(R.id.button).isSuccess { isDisplayed() }
if (isButtonDisplayed) {
    //do some reasonable actions
}
```
***
### Why all Ultron actions and assertions are much more stable?

The framework catches a list of specified exceptions and tries to repeat operation during timeout (5 sec by default). Ofcourse, you are able to customise the list of processed exceptions. It is also available to specify custom timeout for any operation. 

```kotlin
withId(R.id.result).withTimeout(10_000).hasText("Passed")
```
***
## 3 steps to develop a test using Ultron

We try to advocate the correct construction of the test framework architecture, the division of responsibilities between the layers and other proper things.

Therefore, I would like to recommend the following approach when your are using Ultron.

1. Create a PageObject class and specify screen UI elements as `Matcher<View>` objects.

```kotlin
object ChatPage : Page<ChatPage>() {
    private val messagesList = withId(R.id.messages_list)
    private val clearHistoryBtn = withText("Clear history")
    private val inputMessageText = withId(R.id.message_input_text)
    private val sendMessageBtn = withId(R.id.send_button)
}
```

It's recommended to make all PageObject classes as `object` and descendants of Page class.
In this case you will be able to use cool kotlin magic.

2. Describe user step methods in PageObject class.

```kotlin
object ChatPage : Page<ChatPage>() {
    fun sendMessage(text: String) = apply {
        inputMessageText.typeText(text)
        sendMessageBtn.click()
        getMessageListItem(text).text
             .isDisplayed()
             .hasText(text)
    }

    fun clearHistory() = apply {
        openContextualActionModeOverflowMenu()
        clearHistoryBtn.click()
    }
}
```
Full code sample [ChatPage.class](https://github.com/open-tool/ultron/blob/master/sample-app/src/androidTest/java/com/atiurin/sampleapp/pages/ChatPage.kt)

3. Call user steps in test

```kotlin
    @Test
    fun friendsItemCheck(){
        FriendsListPage {
            assertName("Janice")
            assertStatus("Janice","Oh. My. God")
        }
    }
    @Test
    fun sendMessage(){
        FriendsListPage.openChat("Janice")
        ChatPage {
            clearHistory()
            sendMessage("test message")
        }
    }
```
Full code sample [DemoEspressoTest.class](https://github.com/open-tool/ultron/blob/master/sample-app/src/androidTest/java/com/atiurin/sampleapp/tests/espresso/DemoEspressoTest.kt)

In general, it all comes down to the fact that the architecture of your project will look like this.

![Architecture](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/img/architecture.png)

## Add to your project
Gradle
```groovy
repositories {
    mavenCentral()
}

dependencies {
    androidTestImplementation 'com.atiurin:ultron:<latest_version>'
}
```

## AndroidX

It is required to use AndroidX libraries. You can get some problems with Android Support ones.

## Roadmap

- https://github.com/open-tool/ultron/issues/23 Custom assertion on action result
- https://github.com/open-tool/ultron/issues/11 Jetpack Compose support improvement
- https://github.com/open-tool/ultron/issues/21 Add hasTextColor matcher


## Task --

### Step 1 
- Clone repository from – https://github.com/GKObakhidze/Friends-Espresso
- Project name on GitHub  -  Friends-Espresso
- Go to test packages – sample-app  src androidTest  java  com.atiurin.sampleapp

### Step 2 
 - Create branch Text_With_My_Friend and make the following changes in this branch

### Step 3
- Use android studio Layout inspector to find element type or id(you can also use Appium inspector for that )

### Step 4 
- Use page object model: Page, Steps, and Tests packages and place all your following changes there

matchers(pages) packages with an object in it and place your variables there
create step object for actions and use them in test cases:
-    open chat with my friend step (with parameter)
-    enter text step (with parameter)
-    open menu step
-    chose menu item step (with parameter)
-    and others ...
 
### Step 5
 - Create your tests in the test class named --> `ChattingTests.kt` (in espresso test package)

1 TC
 - create kotlin test - fun
 - 1. check that the dashboard is loaded 
    2. open chat with your friend (choose addressee who you wish ) 
    3.Check the chat is open with the right addressee  
    4. Greet a friend with your text (text what you wish) 
   5. assert your sent text in the chat
 - install the App and execute the test on an emulator

2 TC
 - create kotlin test - fun
 - 1. check that the dashboard is loaded 
   2. open Main Menu (Burger Manu)  
   3. choose "Custom Clicks" Option and open it  
   4. check "Custom Clicks" page is open  
   5. Mark all corner circles 
   6. Validate all corner circles are marked. 
 - install the App and execute the test on an emulator


### Step 6
- Try to write clean code, you can use detekt plugin for that (you can add detekt plugin in your android studio)


### Step 7
  -Add project files with new changes in your own repository which will be public
  -Send us your repository link


