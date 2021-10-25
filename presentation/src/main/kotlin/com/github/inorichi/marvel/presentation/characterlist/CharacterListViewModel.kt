package com.github.inorichi.marvel.presentation.characterlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.github.inorichi.marvel.domain.character.interactor.GetCharactersPaginated
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
  private val getCharactersPaginated: GetCharactersPaginated
): ViewModel() {

  private val charactersFlow = Pager(PagingConfig(pageSize = 20)) { getCharactersPaginated }
    .flow
    .cachedIn(viewModelScope)

  val state = flowOf(
    CharacterListViewState(
      characters = charactersFlow
    )
  ).stateIn(viewModelScope, SharingStarted.Eagerly, CharacterListViewState(charactersFlow))

}
