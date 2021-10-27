package com.github.inorichi.marvel.domain.base

/**
 * A page result containing the [data] of the fetched page, the current [page] number and whether
 * it [hasNextPage] and [hasPreviousPage].
 *
 * This class is used in order to not add the pagination dependency to the [data] module.
 */
data class PageResult<T>(
  val data: List<T>,
  val page: Int,
  val hasNextPage: Boolean
) {
  val hasPreviousPage = page > 1
}
