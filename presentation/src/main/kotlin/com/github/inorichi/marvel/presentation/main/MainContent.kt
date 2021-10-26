package com.github.inorichi.marvel.presentation.main

import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@Composable
fun MainContent() {
  MainTheme {
    val navController = rememberAnimatedNavController()
    Navigation(navController = navController, startDestination = Screen.CharacterList.route)
  }
}
