package com.github.inorichi.marvel.data.features.character.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.inorichi.marvel.data.features.character.model.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

  @Query("SELECT * FROM character WHERE id = :characterId")
  fun subscribeById(characterId: Int): Flow<Character?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun save(characters: List<Character>)

}
