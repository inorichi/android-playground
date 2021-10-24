package com.github.inorichi.marvel.data.features.character.datasource

import com.github.inorichi.marvel.data.features.character.mapper.toDbModel
import com.github.inorichi.marvel.data.features.character.mapper.toEntity
import com.github.inorichi.marvel.data.local.AppDatabase
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterLocalDataSource @Inject constructor(
  database: AppDatabase
) {

  private val dao = database.characterDao()

  suspend fun saveCharacters(characters: List<CharacterOverview>) {
    val dbCharacters = characters.map(CharacterOverview::toDbModel)
    dao.save(dbCharacters)
  }

  fun subscribeById(characterId: Int): Flow<CharacterDetails?> {
    return dao.subscribeById(characterId).map { it?.toEntity() }
  }

}
