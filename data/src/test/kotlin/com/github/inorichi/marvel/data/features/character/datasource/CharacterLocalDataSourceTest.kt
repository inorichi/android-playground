package com.github.inorichi.marvel.data.features.character.datasource

import com.github.inorichi.marvel.data.features.character.FakeCharacters
import com.github.inorichi.marvel.data.features.character.dao.CharacterDao
import com.github.inorichi.marvel.data.local.AppDatabase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single

class CharacterLocalDataSourceTest : FunSpec({

  val appDatabase = mockk<AppDatabase>()
  val charactersDao = mockk<CharacterDao>()
  lateinit var dataSource: CharacterLocalDataSource

  beforeTest {
    every { appDatabase.characterDao() } returns charactersDao
    dataSource = CharacterLocalDataSource(appDatabase)
  }

  afterTest {
    clearMocks(appDatabase)
  }

  test("saves characters") {
    coEvery { charactersDao.save(any()) } just Runs
    dataSource.saveCharacters(FakeCharacters.firstPageLocal.data)

    coVerify { charactersDao.save(any()) }
  }

  test("subscribes to character") {
    coEvery { charactersDao.subscribeById(1) } returns flowOf(FakeCharacters.singleDb)
    val result = dataSource.subscribeById(1)

    result.single().shouldBe(FakeCharacters.singleDetails)
  }

})
