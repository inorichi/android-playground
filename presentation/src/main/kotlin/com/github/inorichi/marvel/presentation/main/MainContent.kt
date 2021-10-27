package com.github.inorichi.marvel.presentation.main

import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

/**
 * The entry point of the compose application. Applies the theme and creates the navigation
 * controller.
 */
@Composable
fun MainContent() {
  MainTheme {
    val navController = rememberAnimatedNavController()
    Navigation(navController = navController, startDestination = Screen.CharacterList.route)
  }
}
