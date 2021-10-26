package com.github.inorichi.marvel.presentation.characterlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.presentation.R
import com.github.inorichi.marvel.presentation.characterlist.components.CharacterListContent
import com.github.inorichi.marvel.presentation.characterlist.components.CharacterListEmptyContent
import com.github.inorichi.marvel.presentation.characterlist.components.CharacterListErrorContent

@Composable
fun CharacterListScreen(
  viewModel: CharacterListViewModel = hiltViewModel(),
  onClickCharacter: (Int) -> Unit = {}
) {
  val state by viewModel.state.collectAsState()
  val lazyCharacters = state.characters.collectAsLazyPagingItems()

  Scaffold(
    topBar = {
      TopAppBar(title = { Text(stringResource(R.string.character_list_title)) })
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentAlignment = Alignment.Center
    ) {
      when (lazyCharacters.asScreenState()) {
        CharacterListScreenState.Content -> {
          CharacterListContent(
            lazyCharacters = lazyCharacters,
            onClickCharacter = onClickCharacter
          )
        }
        CharacterListScreenState.Refreshing -> {
          CircularProgressIndicator(
            color = Color.White,
            modifier = Modifier.testTag("CharacterListLoading")
          )
        }
        CharacterListScreenState.Empty -> {
          CharacterListEmptyContent()
        }
        CharacterListScreenState.Error -> {
          CharacterListErrorContent(onRetry = { lazyCharacters.retry() })
        }
      }
    }
  }
}

/**
 * Convert the state of the paging items to the screen state.
 */
private fun LazyPagingItems<CharacterOverview>.asScreenState(): CharacterListScreenState {
  val loadState = loadState
  return when {
    loadState.refresh is LoadState.Loading -> {
      CharacterListScreenState.Refreshing
    }
    loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
      CharacterListScreenState.Error
    }
    itemCount > 0 -> {
      CharacterListScreenState.Content
    }
    loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached -> {
      CharacterListScreenState.Empty
    }
    else -> CharacterListScreenState.Refreshing
  }
}

private enum class CharacterListScreenState {
  Refreshing,
  Empty,
  Error,
  Content
}
