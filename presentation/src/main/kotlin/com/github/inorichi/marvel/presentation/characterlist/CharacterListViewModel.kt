package com.github.inorichi.marvel.presentation.characterlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.domain.character.interactor.GetCharactersPaginated
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
  private val getCharactersPaginated: GetCharactersPaginated
): ViewModel() {

  private val initialQuery: String? = savedStateHandle[QUERY_KEY]

  private var paginationScope = createPaginationScope()

  private val mutableState = MutableStateFlow(
    CharacterListViewState(
      characters = getCharactersFlow(initialQuery),
      query = initialQuery)
  )
  val state: StateFlow<CharacterListViewState> get() = mutableState

  fun resetWithQuery(query: String? = null) {
    val newQuery = if (query.isNullOrBlank()) null else query
    if (newQuery == state.value.query) return

    savedStateHandle[QUERY_KEY] = newQuery
    paginationScope.cancel()
    paginationScope = createPaginationScope()
    mutableState.value = CharacterListViewState(
      characters = getCharactersFlow(newQuery),
      query = newQuery
    )
  }

  private fun getCharactersFlow(query: String?): Flow<PagingData<CharacterOverview>> {
    return Pager(PagingConfig(pageSize = 20)) { getCharactersPaginated(query) }
      .flow
      .cachedIn(paginationScope)
  }

  private fun createPaginationScope(): CoroutineScope {
    return CoroutineScope(Job(viewModelScope.coroutineContext.job) + Dispatchers.Main)
  }

  companion object {
    const val QUERY_KEY = "query"
  }

}
