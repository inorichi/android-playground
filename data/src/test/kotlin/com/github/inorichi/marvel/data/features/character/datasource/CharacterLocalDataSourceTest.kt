package com.github.inorichi.marvel.data.features.character.datasource

import androidx.room.withTransaction
import com.github.inorichi.marvel.data.R
import com.github.inorichi.marvel.data.features.character.FakeCharacters
import com.github.inorichi.marvel.data.features.character.dao.CharacterDao
import com.github.inorichi.marvel.data.local.AppDatabase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.*

class CharacterLocalDataSourceTest : FunSpec({

  val appDatabase = mockk<AppDatabase>()
  val charactersDao = mockk<CharacterDao>()
  lateinit var dataSource: CharacterLocalDataSource

  beforeTest {
    every { appDatabase.characterDao() } returns charactersDao
    dataSource = CharacterLocalDataSource(appDatabase)

    mockkStatic("androidx.room.RoomDatabaseKt") // Needed to mock withTransaction
    val transactionLambda = slot<suspend () -> R>()
    coEvery { appDatabase.withTransaction(capture(transactionLambda)) } coAnswers {
      transactionLambda.captured.invoke()
    }
  }

  afterTest {
    clearMocks(appDatabase)
  }

  test("saves a list of characters") {
    coEvery { charactersDao.saveCharacters(any()) } just Runs

    dataSource.saveCharacters(FakeCharacters.firstPageLocal.data)

    coVerify { charactersDao.saveCharacters(any()) }
  }

  test("saves the details of a character and its relations") {
    coEvery { charactersDao.saveCharacter(any()) } just Runs
    coEvery { charactersDao.saveCharacterComics(any()) } just Runs
    coEvery { charactersDao.saveCharacterSeries(any()) } just Runs

    dataSource.saveCharacterDetails(FakeCharacters.singleDetails)

    coVerify { charactersDao.saveCharacter(any()) }
    coVerify { charactersDao.saveCharacterComics(any()) }
    coVerify { charactersDao.saveCharacterSeries(any()) }
  }

  test("returns a character") {
    coEvery { charactersDao.getCharacter(1) } returns FakeCharacters.singleDb

    val result = dataSource.getCharacterDetails(1)

    result.shouldBe(FakeCharacters.singleDetails)
  }

  test("returns no character") {
    coEvery { charactersDao.getCharacter(1) } returns FakeCharacters.singleDb
    coEvery { charactersDao.getCharacter(2) } returns null

    val result = dataSource.getCharacterDetails(2)

    result.shouldBeNull()
  }

})
