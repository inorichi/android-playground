package com.github.inorichi.marvel.presentation.characterlist

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.domain.character.interactor.GetCharactersPaginated
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import com.github.inorichi.marvel.presentation.main.MainTheme
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test

class CharacterListScreenTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun should_show_content() {
    composeTestRule.setContent {
      val repository = mockk<CharacterRepository>()
      val results = listOf(
        CharacterOverview(id = 1, name = "Iron Man", "")
      )
      coEvery { repository.getCharacters(any()) } returns PageResult(results, 1, false)
      val pagingSource = GetCharactersPaginated(repository)
      val vm = CharacterListViewModel(pagingSource)

      MainTheme {
        CharacterListScreen(vm)
      }
    }

    composeTestRule.onNodeWithText("Iron Man")
      .assertIsDisplayed()
      .assertHasClickAction()
  }

  @Test
  fun should_show_empty_state() {
    composeTestRule.setContent {
      val repository = mockk<CharacterRepository>()
      coEvery { repository.getCharacters(any()) } returns PageResult(emptyList(), 1, false)
      val pagingSource = GetCharactersPaginated(repository)
      val vm = CharacterListViewModel(pagingSource)

      MainTheme {
        CharacterListScreen(vm)
      }
    }

    composeTestRule.onNodeWithText("No characters found")
      .assertIsDisplayed()
  }

  @Test
  fun should_show_loading_state() {
    composeTestRule.setContent {
      val repository = mockk<CharacterRepository>()
      coEvery { repository.getCharacters(any()) } coAnswers {
        delay(10000)
        error("")
      }
      val pagingSource = GetCharactersPaginated(repository)
      val vm = CharacterListViewModel(pagingSource)

      MainTheme {
        CharacterListScreen(vm)
      }
    }

    composeTestRule.onNodeWithTag("CharacterListLoading")
      .assertIsDisplayed()
  }

  @Test
  fun should_show_error_page() {
    composeTestRule.setContent {
      val repository = mockk<CharacterRepository>()
      coEvery { repository.getCharacters(any()) } throws Exception()
      val pagingSource = GetCharactersPaginated(repository)
      val vm = CharacterListViewModel(pagingSource)

      MainTheme {
        CharacterListScreen(vm)
      }
    }

    composeTestRule.onNodeWithText("Retry")
      .assertIsDisplayed()
      .assertHasClickAction()
  }

}
