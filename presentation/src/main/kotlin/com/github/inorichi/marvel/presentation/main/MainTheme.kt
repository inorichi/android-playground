package com.github.inorichi.marvel.presentation.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * The main theme of the application. It also applies the system bars colors.
 */
@Composable
fun MainTheme(content: @Composable () -> Unit) {
  val systemUi = rememberSystemUiController()
  MaterialTheme(darkColors()) {
    systemUi.setSystemBarsColor(MaterialTheme.colors.surface.copy(alpha = 0.7f))
    content()
  }
}
