package com.github.inorichi.marvel.presentation.characterlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.presentation.R

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
      val loadState = lazyCharacters.loadState
      when {
        loadState.refresh is LoadState.Loading -> {
          CircularProgressIndicator(color = Color.White)
        }
        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
          CharacterListError(onRetry = { lazyCharacters.retry() })
        }
        lazyCharacters.itemCount > 0 -> {
          CharacterListContent(
            lazyCharacters = lazyCharacters,
            onClickCharacter = onClickCharacter
          )
        }
        loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached -> {
          CharacterListEmptyContent()
        }
      }
    }
  }
}

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
fun CharacterListItemPlaceholder() {
  Box(
    modifier = Modifier
      .padding(4.dp)
      .clip(MaterialTheme.shapes.small)
      .fillMaxWidth()
      .aspectRatio(1f)
      .background(Color(0xFF222222)),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator(color = Color.White)
  }
}

@Composable
fun CharacterListEmptyContent(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(":|", fontSize = 64.sp)
    Text(
      text = stringResource(R.string.character_list_empty),
      textAlign = TextAlign.Center
    )
  }
}

@Composable
fun CharacterListError(
  onRetry: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(":(", fontSize = 64.sp)
    Text(
      text = stringResource(R.string.character_list_load_error),
      textAlign = TextAlign.Center
    )
    Button(onClick = onRetry) {
      Text(stringResource(R.string.retry))
    }
  }
}

@Composable
private fun MarvelAttributionText(modifier: Modifier = Modifier) {
  Text(
    text = "Data provided by Marvel. © 2021 MARVEL",
    color = Color.White,
    textAlign = TextAlign.Center,
    modifier = modifier
      .fillMaxWidth()
      .background(Color.Black)
      .padding(2.dp)
  )
}
