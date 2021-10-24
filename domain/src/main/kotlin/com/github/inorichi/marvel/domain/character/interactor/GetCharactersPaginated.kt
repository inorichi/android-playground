package com.github.inorichi.marvel.domain.character.interactor

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersPaginated @Inject constructor(
  private val repository: CharacterRepository
) : PagingSource<Int, CharacterOverview>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterOverview> {
    val page = params.key ?: 1

    val result = try {
      repository.getCharacters(page)
    } catch (error: Exception) {
      return LoadResult.Error(error)
    }

    return LoadResult.Page(
      data = result.data,
      prevKey = (page - 1).takeIf { result.hasPreviousPage },
      nextKey = (page + 1).takeIf { result.hasNextPage }
    )
  }

  override fun getRefreshKey(state: PagingState<Int, CharacterOverview>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestItemToPosition(anchorPosition)?.id
    }
  }

}
