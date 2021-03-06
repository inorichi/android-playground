package com.github.inorichi.marvel.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.inorichi.marvel.data.features.character.dao.CharacterDao
import com.github.inorichi.marvel.data.features.character.model.Character
import com.github.inorichi.marvel.data.features.character.model.CharacterComic
import com.github.inorichi.marvel.data.features.character.model.CharacterSeries

/**
 * The application's Room database.
 */
@Database(entities = [
  Character::class,
  CharacterComic::class,
  CharacterSeries::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun characterDao(): CharacterDao
}
