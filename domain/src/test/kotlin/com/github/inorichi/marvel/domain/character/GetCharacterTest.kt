package com.github.inorichi.marvel.domain.character

import com.github.inorichi.marvel.domain.character.interactor.GetCharacter
import com.github.inorichi.marvel.domain.character.interactor.GetCharacter.Result.*
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class GetCharacterTest : FunSpec({

  val repository = mockk<CharacterRepository>()
  val interactor = GetCharacter(repository)

  afterTest {
    clearMocks(repository)
  }

  test("returns character from repository") {
    coEvery { repository.getCharacter(1) } returns FakeCharacters.singleDetails

    val result = interactor(1)

    coVerify { repository.getCharacter(1) }
    result.shouldBeInstanceOf<Success>()
    // Testing every field for coverage
    result.character.shouldBeEqualToComparingFields(FakeCharacters.singleDetails)
    result.character.comics.first()
      .shouldBeEqualToComparingFields(FakeCharacters.singleDetails.comics.first())
    result.character.series.first()
      .shouldBeEqualToComparingFields(FakeCharacters.singleDetails.series.first())
  }

  test("returns not found from repository") {
    coEvery { repository.getCharacter(1) } returns FakeCharacters.singleDetails
    coEvery { repository.getCharacter(2) } returns null

    val result = interactor(2)

    coVerify { repository.getCharacter(2) }
    result.shouldBeInstanceOf<NotFound>()
  }

  test("returns error from repository") {
    val error = Exception("fetching error")
    coEvery { repository.getCharacter(1) } throws error

    val result = interactor(1)

    coVerify { interactor(1) }
    result.shouldBeInstanceOf<Error>()
    result.error.shouldBe(error)
  }

})
