package com.github.inorichi.marvel.presentation.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@Composable
fun MainContent() {
  MaterialTheme(darkColors()) {
    val navController = rememberAnimatedNavController()
    Navigation(navController = navController, startDestination = Screen.CharacterList.route)
  }
}
