package com.github.inorichi.marvel.presentation.characterdetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.presentation.R

@Composable
fun CharacterDetailsContent(
  character: CharacterDetails,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    item {
      Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = rememberImagePainter(character.thumbnail),
          contentDescription = character.name,
          modifier = Modifier
            .width(200.dp)
            .aspectRatio(3 / 4f)
        )
      }
    }
    if (character.description.isNotBlank()) {
      item {
        Column(
          modifier = Modifier.padding(top = 16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          CharacterDetailsHeader(
            title = stringResource(R.string.character_description),
            imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_label_24)
          )
          Text(character.description, modifier = Modifier.padding(start = 40.dp))
        }
      }
    }
    if (character.series.isNotEmpty()) {
      item {
        CharacterDetailsHeader(
          title = stringResource(R.string.character_series),
          imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_movie_24),
          modifier = Modifier.padding(top = 16.dp)
        )
      }
      items(character.series) { series ->
        Text(series.name, modifier = Modifier.padding(start = 40.dp, top = 8.dp))
      }
    }
    if (character.comics.isNotEmpty()) {
      item {
        CharacterDetailsHeader(
          title = stringResource(R.string.character_comics).uppercase(),
          imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_chrome_reader_mode_24),
          modifier = Modifier.padding(top = 16.dp)
        )
      }
      items(character.comics) { comic ->
        Text(comic.name, modifier = Modifier.padding(start = 40.dp, top = 8.dp))
      }
    }
  }
}

@Composable
private fun CharacterDetailsHeader(
  title: String,
  imageVector: ImageVector,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = imageVector,
        contentDescription = title
      )
      Text(
        text = title.uppercase(),
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        letterSpacing = 1.25.sp
      )
    }
    Divider(startIndent = 40.dp)
  }
}
