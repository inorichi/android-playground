package com.github.inorichi.marvel.data.features.character.repository

import com.github.inorichi.marvel.data.features.character.FakeCharacters
import com.github.inorichi.marvel.data.features.character.datasource.CharacterLocalDataSource
import com.github.inorichi.marvel.data.features.character.datasource.CharacterRemoteDataSource
import com.github.inorichi.marvel.data.remote.api.MarvelApiException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.*

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

  test("retrieves a character from remote data source and saves it to local source") {
    coEvery { remoteDataSource.getCharacterDetails(1) } returns FakeCharacters.singleDetails
    coEvery { localDataSource.saveCharacterDetails(any()) } just Runs

    val result = repository.getCharacter(1)

    coVerify { remoteDataSource.getCharacterDetails(1) }
    coVerify { localDataSource.saveCharacterDetails(any()) }
    coVerify(exactly = 0) { localDataSource.getCharacterDetails(1) }
    result.shouldBe(FakeCharacters.singleDetails)
  }

  test("retrieves a character from local data source when remote source throws") {
    coEvery { remoteDataSource.getCharacterDetails(1) } throws MarvelApiException.UnreachableError("")
    coEvery { localDataSource.saveCharacterDetails(any()) } just Runs
    coEvery { localDataSource.getCharacterDetails(1) } returns FakeCharacters.singleDetails

    val result = repository.getCharacter(1)

    coVerify(exactly = 0) { localDataSource.saveCharacterDetails(any()) }
    coVerify { localDataSource.getCharacterDetails(1) }
    result.shouldBe(FakeCharacters.singleDetails)
  }

  test("does not find a character from local data source when remote source throws") {
    coEvery { remoteDataSource.getCharacterDetails(1) } throws MarvelApiException.UnreachableError("")
    coEvery { localDataSource.saveCharacterDetails(any()) } just Runs
    coEvery { localDataSource.getCharacterDetails(1) } returns null

    shouldThrow<MarvelApiException.UnreachableError> {
      repository.getCharacter(1)
    }

    coVerify(exactly = 0) { localDataSource.saveCharacterDetails(any()) }
    coVerify { localDataSource.getCharacterDetails(1) }
  }

  test("does not find a character from remote data source") {
    coEvery { remoteDataSource.getCharacterDetails(1) } returns null

    val result = repository.getCharacter(1)

    coVerify(exactly = 0) { localDataSource.saveCharacterDetails(any()) }
    coVerify(exactly = 0) { localDataSource.getCharacterDetails(1) }
    result.shouldBeNull()
  }

})
