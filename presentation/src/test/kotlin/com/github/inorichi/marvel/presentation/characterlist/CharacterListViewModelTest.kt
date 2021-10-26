package com.github.inorichi.marvel.presentation.characterlist

import com.github.inorichi.marvel.domain.character.interactor.GetCharactersPaginated
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.clearMocks
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest : FunSpec({

  val getCharactersPaginated = mockk<GetCharactersPaginated>()

  beforeTest {
    Dispatchers.setMain(Dispatchers.Unconfined)
  }

  afterTest {
    clearMocks(getCharactersPaginated)
  }

  test("view model is created") {
    val viewModel = CharacterListViewModel(getCharactersPaginated)
    viewModel.shouldNotBeNull()
  }

})
