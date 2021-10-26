package com.github.inorichi.marvel.domain.character.repository

import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview

interface CharacterRepository {

  suspend fun getCharacters(page: Int, query: String? = null): PageResult<CharacterOverview>

  suspend fun getCharacter(characterId: Int): CharacterDetails?

}
