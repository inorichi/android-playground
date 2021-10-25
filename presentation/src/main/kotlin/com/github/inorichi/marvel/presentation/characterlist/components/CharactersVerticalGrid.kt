package com.github.inorichi.marvel.presentation.characterlist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.CharactersGrid(
  lazyCharacters: LazyPagingItems<CharacterOverview>,
  minSize: Dp,
  onClickCharacter: (Int) -> Unit
) {
  BoxWithConstraints(modifier = Modifier.weight(1f)) {
    // At the moment the only way to know the number of columns of an adaptive grid is to copy
    // the same logic from the implementation.
    val numColumns = maxOf((maxWidth / minSize).toInt(), 1)

    LazyVerticalGrid(cells = GridCells.Adaptive(minSize)) {
      items(lazyCharacters.itemCount) { index ->
        lazyCharacters[index]?.let { character ->
          CharacterListItem(
            character = character,
            onClickCharacter = { onClickCharacter(character.id) }
          )
        }
      }
      // Add placeholders when appending pages
      if (lazyCharacters.loadState.append is LoadState.Loading) {
        val numPlaceholders = numColumns - (lazyCharacters.itemCount % numColumns)
        repeat(numPlaceholders) {
          item {
            CharacterListItemPlaceholder()
          }
        }
      }
    }
  }
}
