package com.github.inorichi.marvel.domain.character.interactor

import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacter @Inject constructor(
  private val repository: CharacterRepository
) {

  fun subscribe(characterId: Int): Flow<CharacterDetails?> {
    return repository.subscribeToCharacter(characterId)
  }

}
