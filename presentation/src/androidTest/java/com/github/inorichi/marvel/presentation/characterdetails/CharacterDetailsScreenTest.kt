package com.github.inorichi.marvel.presentation.characterdetails

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.github.inorichi.marvel.domain.character.entity.CharacterComic
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterSeries
import com.github.inorichi.marvel.presentation.main.MainTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class CharacterDetailsScreenTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun should_show_content() {
    composeTestRule.setContent {
      val vm = mockk<CharacterDetailsViewModel>()
      val character = CharacterDetails(
        id = 1,
        name = "Iron Man",
        description = "Some description",
        thumbnail = "",
        comics = listOf(CharacterComic("Some comic", "")),
        series = listOf(CharacterSeries("Some series", "")),
      )
      every { vm.state } returns MutableStateFlow(CharacterDetailsViewState(character = character))

      MainTheme {
        CharacterDetailsScreen(vm)
      }
    }

    composeTestRule.onNodeWithText("Iron Man")
      .assertIsDisplayed()

    composeTestRule.onNodeWithText("Some description")
      .assertIsDisplayed()

    composeTestRule.onNodeWithText("Some comic")
      .assertIsDisplayed()

    composeTestRule.onNodeWithText("Some series")
      .assertIsDisplayed()
  }

  @Test
  fun should_show_empty_state() {
    composeTestRule.setContent {
      val vm = mockk<CharacterDetailsViewModel>()
      every { vm.state } returns MutableStateFlow(CharacterDetailsViewState())

      MainTheme {
        CharacterDetailsScreen(vm)
      }
    }

    composeTestRule.onNodeWithText("The character was not found")
      .assertIsDisplayed()
  }

  @Test
  fun should_show_loading_state() {
    composeTestRule.setContent {
      val vm = mockk<CharacterDetailsViewModel>()
      every { vm.state } returns MutableStateFlow(CharacterDetailsViewState(isLoading = true))

      MainTheme {
        CharacterDetailsScreen(vm)
      }
    }

    composeTestRule.onNodeWithTag("CharacterDetailsLoading")
      .assertIsDisplayed()
  }

  @Test
  fun should_show_error_page() {
    composeTestRule.setContent {
      val vm = mockk<CharacterDetailsViewModel>()
      every { vm.state } returns MutableStateFlow(CharacterDetailsViewState(error = Exception()))

      MainTheme {
        CharacterDetailsScreen(vm)
      }
    }

    composeTestRule.onNodeWithText("Retry")
      .assertIsDisplayed()
      .assertHasClickAction()
  }

}
