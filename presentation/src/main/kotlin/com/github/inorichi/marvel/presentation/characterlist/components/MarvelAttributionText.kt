package com.github.inorichi.marvel.presentation.characterlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MarvelAttributionText(modifier: Modifier = Modifier) {
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
