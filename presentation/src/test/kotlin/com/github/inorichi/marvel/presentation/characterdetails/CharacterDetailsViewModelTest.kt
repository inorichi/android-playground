package com.github.inorichi.marvel.presentation.characterdetails

import androidx.lifecycle.SavedStateHandle
import com.github.inorichi.marvel.domain.character.entity.CharacterComic
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterSeries
import com.github.inorichi.marvel.domain.character.interactor.GetCharacter
import com.github.inorichi.marvel.domain.character.interactor.GetCharacter.Result.*
import com.github.inorichi.marvel.presentation.characterdetails.CharacterDetailsViewModel.Companion.CHARACTER_KEY
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModelTest : FunSpec({

  val savedStateHandle = mockk<SavedStateHandle>()
  val getCharacter = mockk<GetCharacter>()

  beforeTest {
    Dispatchers.setMain(Dispatchers.Unconfined)
    every { savedStateHandle.get<Int>(CHARACTER_KEY) } returns 1
  }

  afterTest {
    clearMocks(getCharacter, savedStateHandle)
  }

  test("initializes empty state") {
    coEvery { getCharacter(1) } coAnswers {
      delay(10000)
      NotFound
    }
    val vm = CharacterDetailsViewModel(savedStateHandle, getCharacter)

    val state = vm.state.value
    state.character.shouldBeNull()
    state.error.shouldBeNull()
    state.isLoading.shouldBeTrue()
  }

  test("updates state with character") {
    val character = CharacterDetails(
      id = 1,
      name = "Character 1",
      description = "Some description",
      thumbnail = "https://localhost/nonexistent.jpg",
      comics = listOf(
        CharacterComic("Comic 1", "https://localhost/comic_1"),
        CharacterComic("Comic 2", "https://localhost/comic_2"),
      ),
      series = listOf(
        CharacterSeries("Series 1", "https://localhost/series_1"),
        CharacterSeries("Series 2", "https://localhost/series_2"),
      )
    )
    coEvery { getCharacter(1) } returns Success(character)
    val vm = CharacterDetailsViewModel(savedStateHandle, getCharacter)

    val state = vm.state.value
    state.character.shouldBe(character)
    state.error.shouldBeNull()
    state.isLoading.shouldBeFalse()
  }

  test("updates state with character not found") {
    coEvery { getCharacter(1) } returns NotFound
    val vm = CharacterDetailsViewModel(savedStateHandle, getCharacter)

    val state = vm.state.value
    state.character.shouldBeNull()
    state.error.shouldBeNull()
    state.isLoading.shouldBeFalse()
  }

  test("updates state with error") {
    val error = Exception("Fail to load")
    coEvery { getCharacter(1) } returns Error(error)
    val vm = CharacterDetailsViewModel(savedStateHandle, getCharacter)

    val state = vm.state.value
    state.character.shouldBeNull()
    state.error.shouldBe(error)
    state.isLoading.shouldBeFalse()
  }

})
