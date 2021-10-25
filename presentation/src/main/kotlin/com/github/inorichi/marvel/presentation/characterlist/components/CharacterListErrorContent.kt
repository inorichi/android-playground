package com.github.inorichi.marvel.presentation.characterlist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.inorichi.marvel.presentation.R

@Composable
fun CharacterListErrorContent(
  onRetry: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.padding(horizontal = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(":(", fontSize = 64.sp)
    Text(
      text = stringResource(R.string.character_details_load_error),
      textAlign = TextAlign.Center
    )
    Button(onClick = onRetry) {
      Text(stringResource(R.string.retry))
    }
  }
}
