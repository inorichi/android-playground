package com.github.inorichi.marvel.domain.character.interactor

import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import javax.inject.Inject

class GetCharacter @Inject constructor(
  private val repository: CharacterRepository
) {

  suspend fun await(characterId: Int): Result {
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

  sealed class Result {
    data class Success(val character: CharacterDetails) : Result()
    object NotFound : Result()
    data class Error(val error: Exception) : Result()
  }

}
