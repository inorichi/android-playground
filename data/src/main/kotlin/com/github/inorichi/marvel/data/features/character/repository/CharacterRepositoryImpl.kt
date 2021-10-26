package com.github.inorichi.marvel.data.features.character.repository

import com.github.inorichi.marvel.data.features.character.datasource.CharacterLocalDataSource
import com.github.inorichi.marvel.data.features.character.datasource.CharacterRemoteDataSource
import com.github.inorichi.marvel.data.remote.api.MarvelApiException
import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
  private val localDataSource: CharacterLocalDataSource,
  private val remoteDataSource: CharacterRemoteDataSource
): CharacterRepository {

  override suspend fun getCharacters(page: Int, query: String?): PageResult<CharacterOverview> {
    // Get characters from remote data source
    val characters = remoteDataSource.getCharacters(page, query)

    // Save them to the local data source
    localDataSource.saveCharacters(characters.data)

    // Return the characters from the remote data source
    return characters
  }

  override suspend fun getCharacter(characterId: Int): CharacterDetails? {
    // Get character from remote data source
    return try {
      val character = remoteDataSource.getCharacterDetails(characterId)
      if (character != null) {
        localDataSource.saveCharacterDetails(character)
      }
      character
    } catch (error: MarvelApiException) {
      // If an error occurs, try to fetch from local data source or rethrow
      localDataSource.getCharacterDetails(characterId) ?: throw error
    }
  }

}
