package com.github.inorichi.marvel.presentation.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.github.inorichi.marvel.TestActivity
import com.github.inorichi.marvel.data.DataModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

/**
 * An overall test for checking application launch alongside Jetpack Compose.
 */
@UninstallModules(DataModule::class)
@HiltAndroidTest
class MainContentTest {

  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val composeTestRule = createAndroidComposeRule<TestActivity>()

  @Test
  fun should_show_main_content() {
    composeTestRule.setContent {
      MainContent()
    }

    // We will get an error page because we're failing every network request
    composeTestRule.onNodeWithText(":(")
      .assertIsDisplayed()
  }

}
