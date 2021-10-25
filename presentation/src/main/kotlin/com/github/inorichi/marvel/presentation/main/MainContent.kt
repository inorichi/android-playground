package com.github.inorichi.marvel.presentation.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainContent() {
  val colors = darkColors()
  val systemUi = rememberSystemUiController()
  MaterialTheme(colors) {
    systemUi.setStatusBarColor(colors.surface.copy(alpha = 0.7f))

    val navController = rememberAnimatedNavController()
    Navigation(navController = navController, startDestination = Screen.CharacterList.route)
  }
}
