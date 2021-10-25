package com.github.inorichi.marvel.data.features.character.dao

import android.app.Application
import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import com.github.inorichi.marvel.data.features.character.FakeCharacters
import com.github.inorichi.marvel.data.local.AppDatabase
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.robolectric.RobolectricTest
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull

@RobolectricTest
class CharacterDaoTest : FunSpec({

  lateinit var database: AppDatabase
  lateinit var dao: CharacterDao

  beforeTest {
    val context = ApplicationProvider.getApplicationContext<Application>()
    database = Room.databaseBuilder(context, AppDatabase::class.java, "marvel-db-test").build()
    dao = database.characterDao()
  }

  test("returns no characters") {
    val result = dao.getCharacter(1)
    result.shouldBeNull()
  }

  test("saves and returns character list") {
    val characters = FakeCharacters.dbPage
    dao.saveCharacters(characters)

    val result = dao.getCharacter(1)
    result.shouldNotBeNull()
    result.character.shouldBeEqualToComparingFields(FakeCharacters.dbPage.first { it.id == 1 })
  }

  test("saves and returns character") {
    dao.saveCharacter(FakeCharacters.singleDb.character)

    val result = dao.getCharacter(1)
    result.shouldNotBeNull()
    result.character.shouldBeEqualToComparingFields(FakeCharacters.singleDb.character)
  }

  test("saves and returns character and its relations") {
    val character = FakeCharacters.singleDb.character
    val comics = FakeCharacters.getComicList(1)
    val series = FakeCharacters.getSeriesList(1)

    database.withTransaction {
      dao.saveCharacter(character)
      dao.saveCharacterComics(comics)
      dao.saveCharacterSeries(series)
    }
    val result = dao.getCharacter(1)

    result.shouldNotBeNull()
    result.comics.shouldHaveSize(2)
    result.series.shouldHaveSize(2)
  }

})
