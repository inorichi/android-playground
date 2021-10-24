package com.github.inorichi.marvel.data.features.character.repository

import com.github.inorichi.marvel.data.features.character.FakeCharacters
import com.github.inorichi.marvel.data.features.character.datasource.CharacterLocalDataSource
import com.github.inorichi.marvel.data.features.character.datasource.CharacterRemoteDataSource
import io.kotest.core.spec.style.FunSpec
import io.mockk.*
import kotlinx.coroutines.flow.flowOf

class CharacterRepositoryImplTest : FunSpec({

  val localDataSource = mockk<CharacterLocalDataSource>()
  val remoteDataSource = mockk<CharacterRemoteDataSource>()
  val repository = CharacterRepositoryImpl(localDataSource, remoteDataSource)

  afterTest {
    clearMocks(localDataSource, remoteDataSource)
  }

  test("gets characters from remote data source") {
    coEvery { remoteDataSource.getCharacters(1) } returns FakeCharacters.firstPageLocal
    coEvery { localDataSource.saveCharacters(any()) } just Runs
    repository.getCharacters(1)

    coVerify { remoteDataSource.getCharacters(1) }
  }

  test("saves characters to local data source") {
    coEvery { remoteDataSource.getCharacters(1) } returns FakeCharacters.firstPageLocal
    coEvery { localDataSource.saveCharacters(any()) } just Runs
    repository.getCharacters(1)

    coVerify { localDataSource.saveCharacters(FakeCharacters.firstPageLocal.data) }
  }

  test("subscribes to a given character") {
    coEvery { localDataSource.subscribeById(1) } returns flowOf(FakeCharacters.singleDetails)
    repository.subscribeToCharacter(1)

    coVerify { localDataSource.subscribeById(1) }
  }

})
