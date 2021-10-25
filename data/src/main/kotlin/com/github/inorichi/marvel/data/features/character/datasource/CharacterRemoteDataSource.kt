package com.github.inorichi.marvel.data.features.character.datasource

import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.data.features.character.mapper.toEntity
import com.github.inorichi.marvel.data.remote.api.MarvelApi
import com.github.inorichi.marvel.data.remote.api.MarvelApiConstants
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRemoteDataSource @Inject constructor(private val api: MarvelApi) {

  suspend fun getCharacters(page: Int): PageResult<CharacterOverview> {
    val offset = (page - 1) * MarvelApiConstants.PAGE_LIMIT
    val characters = api.getCharacters(offset = offset, limit = MarvelApiConstants.PAGE_LIMIT)
    val data = characters.toEntity()
    val hasNextPage = with(characters.data) { offset + limit < total }
    return PageResult(
      data = data,
      page = page,
      hasNextPage = hasNextPage
    )
  }

}
