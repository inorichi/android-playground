package com.github.inorichi.marvel.presentation.characterlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.presentation.R
import kotlinx.coroutines.flow.Flow

@Composable
fun CharacterListScreen(
  viewModel: CharacterListViewModel = hiltViewModel(),
  onClickCharacter: (Int) -> Unit = {}
) {
  Scaffold(
    topBar = {
      TopAppBar(title = { Text(stringResource(R.string.character_list_title)) })
    }
  ) { paddingValues ->
    CharacterListContent(
      characters = viewModel.characters,
      onClickCharacter = onClickCharacter,
      modifier = Modifier.padding(paddingValues)
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterListContent(
  characters: Flow<PagingData<CharacterOverview>>,
  onClickCharacter: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  val lazyCharacters = characters.collectAsLazyPagingItems()
  Column(modifier = modifier) {
    LazyVerticalGrid(cells = GridCells.Adaptive(160.dp), modifier = Modifier.weight(1f)) {
      items(lazyCharacters.itemCount) { index ->
        lazyCharacters[index]?.let { character ->
          CharacterListItem(
            character = character,
            onClickCharacter = { onClickCharacter(character.id) }
          )
        }
      }
    }
    MarvelAttributionText(modifier = Modifier.align(Alignment.CenterHorizontally))
  }
}

@Composable
fun CharacterListItem(character: CharacterOverview, onClickCharacter: () -> Unit) {
  Box(
    modifier = Modifier
      .padding(4.dp)
      .clip(MaterialTheme.shapes.small)
      .clickable(onClick = onClickCharacter)
  ) {
    Image(
      painter = rememberImagePainter(character.thumbnail),
      contentDescription = character.name,
      alignment = Alignment.TopCenter,
      modifier = Modifier.aspectRatio(1f)
    )
    Text(
      text = character.name,
      color = Color.White,
      maxLines = 2,
      modifier = Modifier
        .align(Alignment.BottomStart)
        .fillMaxWidth()
        .background(Color.Black)
        .padding(4.dp)
    )
  }
}

@Composable
private fun MarvelAttributionText(modifier: Modifier = Modifier) {
  Text(
    text = "Data provided by Marvel. © 2021 MARVEL",
    color = Color.White,
    modifier = modifier
      .fillMaxWidth()
      .background(Color.Black)
      .padding(2.dp)
  )
}
