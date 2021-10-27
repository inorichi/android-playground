package com.github.inorichi.marvel.domain.character.repository

import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview

/**
 * Repository interface for retrieving Marvel characters.
 */
interface CharacterRepository {

  /**
   * Returns a paginated result of characters given a [page] and an optional [query] or throws an
   * error if something fails.
   */
  suspend fun getCharacters(page: Int, query: String? = null): PageResult<CharacterOverview>

  /**
   * Returns the details of a character, null if not found or an exception in case of an error.
   */
  suspend fun getCharacter(characterId: Int): CharacterDetails?

}
