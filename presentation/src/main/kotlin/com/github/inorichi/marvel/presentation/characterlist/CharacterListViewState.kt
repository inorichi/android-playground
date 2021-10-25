package com.github.inorichi.marvel.presentation.characterlist

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import kotlinx.coroutines.flow.Flow

@Immutable
data class CharacterListViewState(
  val characters: Flow<PagingData<CharacterOverview>>
)
