package com.github.inorichi.marvel.domain.character

import com.github.inorichi.marvel.domain.character.interactor.GetCharacter
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

class GetCharacterTest : FunSpec({

  val repository = mockk<CharacterRepository>()
  val interactor = GetCharacter(repository)

  afterTest {
    clearMocks(repository)
  }

  test("subscribes to character in repository") {
    every { interactor.subscribe(1) } returns flowOf(FakeCharacters.singleDetails)
    val flow = interactor.subscribe(1)
    verify { interactor.subscribe(1) }

    val results = flow.toList()
    results.shouldHaveSize(1)
    val character = results.first()
    character.shouldNotBeNull()

    // Testing every field for coverage
    character.shouldBeEqualToComparingFields(FakeCharacters.singleDetails)
  }

  test("subscribes to character not found in repository") {
    every { interactor.subscribe(1) } returns flowOf(FakeCharacters.singleDetails)
    every { interactor.subscribe(2) } returns flowOf(null)
    val flow = interactor.subscribe(2)
    verify { interactor.subscribe(2) }

    val results = flow.toList()
    results.shouldHaveSize(1)
    val character = results.first()
    character.shouldBeNull()
  }

})
