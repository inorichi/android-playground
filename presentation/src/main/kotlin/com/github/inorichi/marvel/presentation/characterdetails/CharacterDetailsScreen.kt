package com.github.inorichi.marvel.presentation.characterdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.inorichi.marvel.presentation.R

@Composable
fun CharacterDetailsScreen(
  viewModel: CharacterDetailsViewModel = hiltViewModel(),
  navigateUp: () -> Unit
) {
  val character = viewModel.character

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(character?.name ?: "")
        },
        navigationIcon = {
          IconButton(onClick = navigateUp) {
            Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
          }
        }
      )
    }
  ) {
    Box(Modifier.fillMaxSize()) {
      Text("Character id: ${viewModel.character}")
    }
  }

}
