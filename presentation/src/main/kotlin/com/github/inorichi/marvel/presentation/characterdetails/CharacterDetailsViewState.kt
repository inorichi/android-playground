package com.github.inorichi.marvel.presentation.characterdetails

import androidx.compose.runtime.Immutable
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails

/**
 * View state of the character details screen. It contains a [character] (null if not found),
 * whether it [isLoading] and an error if something has failed.
 */
@Immutable
data class CharacterDetailsViewState(
  val character: CharacterDetails? = null,
  val isLoading: Boolean = false,
  val error: Exception? = null
) {
  companion object {
    val Empty = CharacterDetailsViewState(
      character = null,
      isLoading = true,
      error = null
    )
  }
}
