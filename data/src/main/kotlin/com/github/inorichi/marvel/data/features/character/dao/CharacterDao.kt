package com.github.inorichi.marvel.data.features.character.dao

import androidx.room.*
import com.github.inorichi.marvel.data.features.character.model.Character
import com.github.inorichi.marvel.data.features.character.model.CharacterComic
import com.github.inorichi.marvel.data.features.character.model.CharacterSeries
import com.github.inorichi.marvel.data.features.character.model.CharacterWithRelations

/**
 * A character DAO interface for Room queries.
 */
@Dao
interface CharacterDao {

  @Transaction // Use transaction because it fetches from several tables
  @Query("SELECT * FROM character WHERE id = :characterId")
  suspend fun getCharacter(characterId: Int): CharacterWithRelations?

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun saveCharacters(characters: List<Character>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveCharacter(character: Character)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveCharacterSeries(series: List<CharacterSeries>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveCharacterComics(comics: List<CharacterComic>)

}
