package com.github.inorichi.marvel.presentation.characterdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.inorichi.marvel.presentation.R
import com.github.inorichi.marvel.presentation.characterdetails.components.CharacterDetailsContent
import com.github.inorichi.marvel.presentation.characterdetails.components.CharacterDetailsEmptyContent
import com.github.inorichi.marvel.presentation.characterdetails.components.CharacterDetailsErrorContent

@Composable
fun CharacterDetailsScreen(
  viewModel: CharacterDetailsViewModel = hiltViewModel(),
  navigateUp: () -> Unit = {}
) {
  val state by viewModel.state.collectAsState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(state.character?.name ?: "")
        },
        navigationIcon = {
          IconButton(onClick = navigateUp) {
            Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
          }
        }
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentAlignment = Alignment.Center
    ) {
      when (state.asScreenState()) {
        CharacterDetailsScreenState.Loading -> {
          CircularProgressIndicator(
            color = Color.White,
            modifier = Modifier.testTag("CharacterDetailsLoading")
          )
        }
        CharacterDetailsScreenState.Content -> {
          CharacterDetailsContent(character = state.character!!)
        }
        CharacterDetailsScreenState.Empty -> {
          CharacterDetailsEmptyContent()
        }
        CharacterDetailsScreenState.Error -> {
          CharacterDetailsErrorContent(onRetry = { viewModel.getCharacter() })
        }
      }
    }
  }
}

private fun CharacterDetailsViewState.asScreenState(): CharacterDetailsScreenState {
  return when {
    isLoading -> CharacterDetailsScreenState.Loading
    error != null -> CharacterDetailsScreenState.Error
    character != null -> CharacterDetailsScreenState.Content
    else -> CharacterDetailsScreenState.Empty
  }
}

private enum class CharacterDetailsScreenState {
  Loading,
  Content,
  Empty,
  Error
}
