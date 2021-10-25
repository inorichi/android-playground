package com.github.inorichi.marvel.presentation.characterdetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.presentation.R

@Composable
fun CharacterDetailsContent(
  character: CharacterDetails,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier.fillMaxSize().padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Image(
        painter = rememberImagePainter(character.thumbnail),
        contentDescription = character.name,
        modifier = Modifier
          .width(200.dp)
          .aspectRatio(3 / 4f)
      )
    }
    if (character.description.isNotBlank()) {
      item {
        Text(text = buildAnnotatedString {
          withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(R.string.character_description) + "\n")
          }
          append(character.description)
        })
      }
    }
    val wikiUrl = character.wikiUrl
    if (!wikiUrl.isNullOrBlank()) {
      item {
        Text(text = buildAnnotatedString {
          withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(R.string.character_wiki_url) + "\n")
          }
          append(wikiUrl)
        })
      }
    }
    if (character.series.isNotEmpty()) {
      item {
        Text(
          text = stringResource(R.string.character_series),
          fontWeight = FontWeight.Bold
        )
      }
      items(character.series) { series ->
        Text(series.name)
      }
    }
    if (character.comics.isNotEmpty()) {
      item {
        Text(
          text = stringResource(R.string.character_comics),
          fontWeight = FontWeight.Bold
        )
      }
      items(character.comics) { comic ->
        Text(comic.name)
      }
    }
  }
}
