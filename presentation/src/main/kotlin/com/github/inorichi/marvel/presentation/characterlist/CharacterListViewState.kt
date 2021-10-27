package com.github.inorichi.marvel.presentation.characterlist

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import kotlinx.coroutines.flow.Flow

/**
 * View state of the character listing screen. It contains a flow of [characters] paginated, and
 * the currently applied [query].
 */
@Immutable
data class CharacterListViewState(
  val characters: Flow<PagingData<CharacterOverview>>,
  val query: String?
)
