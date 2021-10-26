package com.github.inorichi.marvel.presentation.characterlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.presentation.R
import com.github.inorichi.marvel.presentation.characterlist.components.CharacterListContent
import com.github.inorichi.marvel.presentation.characterlist.components.CharacterListEmptyContent
import com.github.inorichi.marvel.presentation.characterlist.components.CharacterListErrorContent
import com.github.inorichi.marvel.presentation.characterlist.components.CharacterListToolbar

@Composable
fun CharacterListScreen(
  viewModel: CharacterListViewModel = hiltViewModel(),
  onClickCharacter: (Int) -> Unit = {}
) {
  val context = LocalContext.current
  val state by viewModel.state.collectAsState()
  val lazyCharacters = state.characters.collectAsLazyPagingItems()
  val snackbarHostState = remember { SnackbarHostState() }
  val snackbarVisible by remember {
    derivedStateOf { lazyCharacters.loadState.append is LoadState.Error }
  }

  // Manage snackbar to retry when the user clicks on retry or dismiss it when refreshing
  LaunchedEffect(snackbarVisible) {
    if (snackbarVisible) {
      val result = snackbarHostState.showSnackbar(
        message = context.getString(R.string.character_list_append_error),
        actionLabel = context.getString(R.string.retry),
        duration = SnackbarDuration.Indefinite
      )
      if (result == SnackbarResult.ActionPerformed) {
        lazyCharacters.retry()
      }
    } else {
      snackbarHostState.currentSnackbarData?.dismiss()
    }
  }

  Scaffold(
    topBar = {
      CharacterListToolbar(
        query = state.query,
        onQueryChanged = { viewModel.resetWithQuery(it) }
      )
    },
    scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
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
    loadState.refresh is LoadState.Error -> {
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
