package com.github.inorichi.marvel.domain.character.repository

import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

  suspend fun getCharacters(page: Int): PageResult<CharacterOverview>

  fun subscribeToCharacter(characterId: Int): Flow<CharacterDetails?>

}
