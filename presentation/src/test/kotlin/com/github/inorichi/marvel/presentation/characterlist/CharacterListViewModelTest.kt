package com.github.inorichi.marvel.presentation.characterlist

import androidx.lifecycle.SavedStateHandle
import com.github.inorichi.marvel.domain.character.interactor.GetCharactersPaginated
import com.github.inorichi.marvel.presentation.characterlist.CharacterListViewModel.Companion.QUERY_KEY
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest : FunSpec({

  val savedStateHandle = mockk<SavedStateHandle>()
  val getCharactersPaginated = mockk<GetCharactersPaginated>()

  beforeTest {
    Dispatchers.setMain(Dispatchers.Unconfined)
    every { savedStateHandle.set<Any>(any(), any()) } just Runs
  }

  afterTest {
    clearMocks(getCharactersPaginated)
  }

  test("initializes state with empty query") {
    every { savedStateHandle.get<String?>(QUERY_KEY) } returns null
    val viewModel = CharacterListViewModel(savedStateHandle, getCharactersPaginated)

    viewModel.state.value.query.shouldBeNull()
  }

  test("initializes state with a query") {
    every { savedStateHandle.get<String?>(QUERY_KEY) } returns "Iron Man"
    val viewModel = CharacterListViewModel(savedStateHandle, getCharactersPaginated)

    viewModel.state.value.query.shouldBe("Iron Man")
  }

  test("resets the query with a new one and saves the state") {
    every { savedStateHandle.get<String?>(QUERY_KEY) } returns null
    val viewModel = CharacterListViewModel(savedStateHandle, getCharactersPaginated)

    viewModel.resetWithQuery("Hulk")

    viewModel.state.value.query.shouldBe("Hulk")
    verify { savedStateHandle.set(QUERY_KEY, "Hulk") }
  }

  test("does not update the query if it's the same") {
    every { savedStateHandle.get<String?>(QUERY_KEY) } returns "Iron Man"
    val viewModel = CharacterListViewModel(savedStateHandle, getCharactersPaginated)

    viewModel.resetWithQuery("Iron Man")

    verify(exactly = 1) { savedStateHandle.set<Any>(QUERY_KEY, any()) }
  }

})
