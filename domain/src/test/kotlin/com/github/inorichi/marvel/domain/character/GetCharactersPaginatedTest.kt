package com.github.inorichi.marvel.domain.character

import androidx.paging.PagingSource
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.domain.character.interactor.GetCharactersPaginated
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk

class GetCharactersPaginatedTest : FunSpec({

  val repository = mockk<CharacterRepository>()
  val interactor = GetCharactersPaginated(repository)

  afterTest {
    clearMocks(repository)
  }

  test("loads the first page from repository") {
    coEvery { repository.getCharacters(1) } returns FakeCharacters.firstPage
    val result = interactor.load(
      PagingSource.LoadParams.Refresh(key = 1, loadSize = 2, placeholdersEnabled = false)
    )

    result.shouldBeInstanceOf<PagingSource.LoadResult.Page<Int, CharacterOverview>>()
    result.data.shouldBe(FakeCharacters.firstPage.data)
    result.prevKey.shouldBeNull()
    result.nextKey.shouldBe(2)
  }

  test("loads the second page from repository") {
    coEvery { repository.getCharacters(2) } returns FakeCharacters.secondPage
    val result = interactor.load(
      PagingSource.LoadParams.Refresh(key = 2, loadSize = 2, placeholdersEnabled = false)
    )

    result.shouldBeInstanceOf<PagingSource.LoadResult.Page<Int, CharacterOverview>>()
    result.data.shouldBe(FakeCharacters.secondPage.data)
    result.prevKey.shouldBe(1)
    result.nextKey.shouldBeNull()
  }

  test("fails to load a page") {
    coEvery { repository.getCharacters(1) } throws Exception("Failed to load page")
    val result = interactor.load(
      PagingSource.LoadParams.Refresh(key = 1, loadSize = 2, placeholdersEnabled = false)
    )

    result.shouldBeInstanceOf<PagingSource.LoadResult.Error<Int, CharacterOverview>>()
  }

})
