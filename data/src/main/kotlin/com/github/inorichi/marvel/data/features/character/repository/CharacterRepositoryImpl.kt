package com.github.inorichi.marvel.data.features.character.repository

import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.data.features.character.datasource.CharacterLocalDataSource
import com.github.inorichi.marvel.data.features.character.datasource.CharacterRemoteDataSource
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
  private val localDataSource: CharacterLocalDataSource,
  private val remoteDataSource: CharacterRemoteDataSource
): CharacterRepository {

  override suspend fun getCharacters(page: Int): PageResult<CharacterOverview> {
    // Get characters from remote data source
    val characters = remoteDataSource.getCharacters(page)

    // Save them to the local data source
    localDataSource.saveCharacters(characters.data)

    // Return the characters from the remote data source
    return characters
  }

  override fun subscribeToCharacter(characterId: Int): Flow<CharacterDetails?> {
    return localDataSource.subscribeById(characterId)
  }

}
