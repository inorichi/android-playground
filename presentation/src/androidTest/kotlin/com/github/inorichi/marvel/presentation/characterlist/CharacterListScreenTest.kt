package com.github.inorichi.marvel.presentation.characterlist

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.domain.character.interactor.GetCharactersPaginated
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import com.github.inorichi.marvel.presentation.main.MainTheme
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterListScreenTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  lateinit var repository: CharacterRepository
  lateinit var savedStateHandle: SavedStateHandle
  lateinit var getCharactersPaginated: GetCharactersPaginated

  @Before
  fun beforeTest() {
    repository = mockk()
    savedStateHandle = mockk()
    getCharactersPaginated = GetCharactersPaginated(repository)
    every { savedStateHandle.get<String?>("query") } returns null
  }

  @Test
  fun should_show_content() {
    composeTestRule.setContent {
      val results = listOf(
        CharacterOverview(id = 1, name = "Iron Man", "")
      )
      coEvery { repository.getCharacters(any()) } returns PageResult(results, 1, false)
      val vm = CharacterListViewModel(savedStateHandle, getCharactersPaginated)

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
      coEvery { repository.getCharacters(any()) } returns PageResult(emptyList(), 1, false)
      val vm = CharacterListViewModel(savedStateHandle, getCharactersPaginated)

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
      coEvery { repository.getCharacters(any()) } coAnswers {
        delay(10000)
        error("")
      }
      val vm = CharacterListViewModel(savedStateHandle, getCharactersPaginated)

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
      coEvery { repository.getCharacters(any()) } throws Exception()
      val vm = CharacterListViewModel(savedStateHandle, getCharactersPaginated)

      MainTheme {
        CharacterListScreen(vm)
      }
    }

    composeTestRule.onNodeWithText("Retry")
      .assertIsDisplayed()
      .assertHasClickAction()
  }

  @Test
  fun should_show_main_toolbar_without_query() {
    composeTestRule.setContent {
      coEvery { repository.getCharacters(any()) } returns PageResult(emptyList(), 1, false)
      val vm = CharacterListViewModel(savedStateHandle, getCharactersPaginated)

      MainTheme {
        CharacterListScreen(vm)
      }
    }

    composeTestRule.onNodeWithContentDescription("Search")
      .assertIsDisplayed()
  }

  @Test
  fun should_show_search_toolbar_with_query() {
    composeTestRule.setContent {
      coEvery { repository.getCharacters(any()) } returns PageResult(emptyList(), 1, false)
      every { savedStateHandle.get<String?>("query") } returns "Random character"
      val vm = CharacterListViewModel(savedStateHandle, getCharactersPaginated)

      MainTheme {
        CharacterListScreen(vm)
      }
    }

    composeTestRule.onNodeWithText("Random character")
      .assertIsDisplayed()
  }

}
