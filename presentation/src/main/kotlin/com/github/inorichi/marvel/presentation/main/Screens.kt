package com.github.inorichi.marvel.presentation.main

/**
 * The list of screens of the application.
 */
sealed class Screen(val route: String) {
  object CharacterList : Screen("characters")
  object CharacterDetails : Screen("characters/{characterId}") {
    fun createRoute(characterId: Int) = "characters/$characterId"
  }
}
