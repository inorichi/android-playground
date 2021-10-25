package com.github.inorichi.marvel.data.features.character.datasource

import androidx.room.withTransaction
import com.github.inorichi.marvel.data.features.character.mapper.toDbModel
import com.github.inorichi.marvel.data.features.character.mapper.toEntity
import com.github.inorichi.marvel.data.local.AppDatabase
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterLocalDataSource @Inject constructor(
  private val database: AppDatabase
) {

  private val dao = database.characterDao()

  suspend fun saveCharacters(characters: List<CharacterOverview>) {
    val dbCharacters = characters.map(CharacterOverview::toDbModel)
    dao.saveCharacters(dbCharacters)
  }

  suspend fun saveCharacterDetails(character: CharacterDetails) {
    database.withTransaction {
      val dbCharacter = character.toDbModel()
      val characterComics = character.comics.map { it.toDbModel(dbCharacter.id) }
      val characterSeries = character.series.map { it.toDbModel(dbCharacter.id) }
      dao.saveCharacter(dbCharacter)
      dao.saveCharacterComics(characterComics)
      dao.saveCharacterSeries(characterSeries)
    }

  }

  suspend fun getCharacterDetails(characterId: Int): CharacterDetails? {
    return dao.getCharacter(characterId)?.toEntity()
  }

}
