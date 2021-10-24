package com.github.inorichi.marvel.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import com.github.inorichi.marvel.presentation.characterdetails.CharacterDetailsScreen
import com.github.inorichi.marvel.presentation.characterlist.CharacterListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
internal fun Navigation(
  navController: NavHostController,
  startDestination: String,
  modifier: Modifier = Modifier
) {
  AnimatedNavHost(
    navController = navController,
    startDestination = startDestination,
    modifier = modifier
  ) {
    addCharacterList(navController)
    addCharacterDetails(navController)
  }
}

private fun NavGraphBuilder.addCharacterList(
  navController: NavController
) {
  composable(Screen.CharacterList.route) {
    CharacterListScreen(
      onClickCharacter = {
        navController.navigate(Screen.CharacterDetails.createRoute(characterId = it) )
      }
    )
  }
}

private fun NavGraphBuilder.addCharacterDetails(
  navController: NavController
) {
  composable(
    route = Screen.CharacterDetails.route,
    arguments = listOf(navArgument("characterId") { type = NavType.IntType })
  ) {
    CharacterDetailsScreen(
      navigateUp = { navController.navigateUp() }
    )
  }
}
