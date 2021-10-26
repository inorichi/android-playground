package com.github.inorichi.marvel.data.features.character.datasource

import com.github.inorichi.marvel.data.features.character.FakeCharacters
import com.github.inorichi.marvel.data.remote.api.MarvelApi
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk

class CharacterRemoteDataSourceTest : FunSpec({

  val api = mockk<MarvelApi>()
  val dataSource = CharacterRemoteDataSource(api)

  afterTest {
    clearMocks(api)
  }

  test("retrieves a list of characters") {
    coEvery { api.getCharacters(any(), any()) } returns FakeCharacters.firstPageRemote
    val result = dataSource.getCharacters(1)

    result.data.shouldHaveSize(20)
    result.hasPreviousPage.shouldBeFalse()
    result.hasNextPage.shouldBeTrue()
  }

  test("retrieves pages until there are no more left") {
    val lastPage = FakeCharacters.firstPageRemote.copy(
      data = FakeCharacters.firstPageRemote.data.copy(
        offset = 20,
        limit = 20,
        total = 40
      )
    )
    coEvery { api.getCharacters(any(), any()) } returns lastPage
    val result = dataSource.getCharacters(2)

    result.data.shouldHaveSize(20)
    result.hasPreviousPage.shouldBeTrue()
    result.hasNextPage.shouldBeFalse()
  }

  test("retrieves the only page") {
    val onlyPage = FakeCharacters.firstPageRemote.copy(
      data = FakeCharacters.firstPageRemote.data.copy(
        offset = 0,
        limit = 20,
        total = 10,
        results = FakeCharacters.firstPageRemote.data.results.take(10)
      )
    )
    coEvery { api.getCharacters(any(), any()) } returns onlyPage
    val result = dataSource.getCharacters(1)

    result.data.shouldHaveSize(10)
    result.hasPreviousPage.shouldBeFalse()
    result.hasNextPage.shouldBeFalse()
  }

  test("retrieves the details of a character") {
    coEvery { api.getCharacterDetails(any()) } returns FakeCharacters.singleDetailsRemote
    val result = dataSource.getCharacterDetails(1)

    result.shouldNotBeNull()
    result.id.shouldBe(1)
    result.name.shouldBe("Character 1")
    result.description.shouldBe("Some description")
    result.comics.shouldHaveSize(2)
    result.series.shouldHaveSize(2)
  }

  test("does not find the details of a character") {
    coEvery { api.getCharacterDetails(any()) } returns FakeCharacters.singleDetailsRemote.copy(
      data = FakeCharacters.singleDetailsRemote.data.copy(results = emptyList())
    )
    val result = dataSource.getCharacterDetails(1)

    result.shouldBeNull()
  }

})
