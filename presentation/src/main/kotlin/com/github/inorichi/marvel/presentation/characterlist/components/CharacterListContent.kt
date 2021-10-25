package com.github.inorichi.marvel.presentation.characterlist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterListContent(
  lazyCharacters: LazyPagingItems<CharacterOverview>,
  onClickCharacter: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    CharactersGrid(lazyCharacters = lazyCharacters, minSize = 160.dp, onClickCharacter = onClickCharacter)
    MarvelAttributionText()
  }
}
