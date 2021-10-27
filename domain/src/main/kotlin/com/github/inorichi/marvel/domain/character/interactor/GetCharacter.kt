package com.github.inorichi.marvel.domain.character.interactor

import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import javax.inject.Inject

/**
 * Use case to retrieve a character given its id.
 */
class GetCharacter @Inject constructor(
  private val repository: CharacterRepository
) {

  /**
   * Awaits the character and handles any possible case, wrapping the result in a [Result] object.
   */
  suspend operator fun invoke(characterId: Int): Result {
    return try {
      val character = repository.getCharacter(characterId)
      if (character != null) {
        Result.Success(character)
      } else {
        Result.NotFound
      }
    } catch (error: Exception) {
      Result.Error(error)
    }
  }

  /**
   * The possible results of invoking this use case.
   */
  sealed class Result {
    data class Success(val character: CharacterDetails) : Result()
    object NotFound : Result()
    data class Error(val error: Exception) : Result()
  }

}
