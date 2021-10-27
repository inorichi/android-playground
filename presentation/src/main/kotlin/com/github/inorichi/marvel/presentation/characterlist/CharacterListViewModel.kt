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

/**
 * View model of the character listing screen.
 */
@HiltViewModel
class CharacterListViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
  private val getCharactersPaginated: GetCharactersPaginated
): ViewModel() {

  /**
   * The initial query of the listing, usually null.
   */
  private val initialQuery: String? = savedStateHandle[QUERY_KEY]

  /**
   * The current pagination scope, as it needs to be cancelled if the query changes.
   */
  private var paginationScope = createPaginationScope()

  /**
   * The state of the screen. Exposed externally as a read-only [StateFlow].
   */
  private val mutableState = MutableStateFlow(
    CharacterListViewState(
      characters = getCharactersFlow(initialQuery),
      query = initialQuery)
  )
  val state: StateFlow<CharacterListViewState> get() = mutableState

  /**
   * Resets the pagination listing with a new [query].
   */
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

  /**
   * Returns the paging flow for this [query] cached in the current [paginationScope].
   */
  private fun getCharactersFlow(query: String?): Flow<PagingData<CharacterOverview>> {
    return Pager(PagingConfig(pageSize = 20)) { getCharactersPaginated(query) }
      .flow
      .cachedIn(paginationScope)
  }

  /**
   * Returns a new pagination scope with the [viewModelScope] as parent in order to be automatically
   * cancelled when the view model is destroyed.
   */
  private fun createPaginationScope(): CoroutineScope {
    return CoroutineScope(Job(viewModelScope.coroutineContext.job) + Dispatchers.Main)
  }

  companion object {
    const val QUERY_KEY = "query"
  }

}
