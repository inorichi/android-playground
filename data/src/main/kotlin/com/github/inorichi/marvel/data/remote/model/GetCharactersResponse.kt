package com.github.inorichi.marvel.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GetCharactersResponse(
  val code: Int,
  val data: Data
) {

  @Serializable
  data class Data(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val results: List<Result>
  )

  @Serializable
  data class Result(
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail
  )

  @Serializable
  data class Thumbnail(
    val path: String,
    val extension: String
  )
}
