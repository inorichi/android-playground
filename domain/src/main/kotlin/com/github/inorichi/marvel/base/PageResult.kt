package com.github.inorichi.marvel.base

data class PageResult<T>(
  val data: List<T>,
  val page: Int,
  val hasNextPage: Boolean
) {
  val hasPreviousPage = page > 1
}
