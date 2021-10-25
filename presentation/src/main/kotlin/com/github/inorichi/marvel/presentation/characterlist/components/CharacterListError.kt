package com.github.inorichi.marvel.presentation.characterlist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
