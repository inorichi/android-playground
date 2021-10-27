package com.github.inorichi.marvel.data.features.character.datasource

import androidx.room.withTransaction
import com.github.inorichi.marvel.data.features.character.mapper.toDbModel
import com.github.inorichi.marvel.data.features.character.mapper.toEntity
import com.github.inorichi.marvel.data.local.AppDatabase
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A local data source for characters using Room [database].
 */
@Singleton
class CharacterLocalDataSource @Inject constructor(
  private val database: AppDatabase
) {

  /**
   * The DAO of characters
   */
  private val dao = database.characterDao()

  /**
   * Saves a list of characters to the database.
   */
  suspend fun saveCharacters(characters: List<CharacterOverview>) {
    val dbCharacters = characters.map(CharacterOverview::toDbModel)
    dao.saveCharacters(dbCharacters)
  }

  /**
   * Saves the details of a character (and the comic and series it appears on) to the database.
   */
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

  /**
   * Returns the details of a character from database.
   */
  suspend fun getCharacterDetails(characterId: Int): CharacterDetails? {
    return dao.getCharacter(characterId)?.toEntity()
  }

}
