package com.github.inorichi.marvel.presentation.characterlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview

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
      contentScale = ContentScale.Crop,
      alignment = Alignment.TopCenter,
      modifier = Modifier.aspectRatio(3/4f)
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
