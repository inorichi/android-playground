package com.github.inorichi.marvel.data.features.character.datasource

import com.github.inorichi.marvel.data.features.character.mapper.toEntity
import com.github.inorichi.marvel.data.remote.api.MarvelApi
import com.github.inorichi.marvel.data.remote.api.MarvelApiConstants
import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A remote data source for characters using the Marvel [api].
 */
@Singleton
class CharacterRemoteDataSource @Inject constructor(private val api: MarvelApi) {

  /**
   * Returns the requested [page] of characters with an optional [query]
   */
  suspend fun getCharacters(page: Int, query: String? = null): PageResult<CharacterOverview> {
    val offset = (page - 1) * MarvelApiConstants.PAGE_LIMIT
    val characters = api.getCharacters(
      offset = offset,
      limit = MarvelApiConstants.PAGE_LIMIT,
      name = query
    )
    val data = characters.toEntity()
    val hasNextPage = with(characters.data) { offset + limit < total }
    return PageResult(
      data = data,
      page = page,
      hasNextPage = hasNextPage
    )
  }

  /**
   * Returns the character details of the requested [characterId].
   */
  suspend fun getCharacterDetails(characterId: Int): CharacterDetails? {
    return api.getCharacterDetails(characterId).toEntity()
  }

}
