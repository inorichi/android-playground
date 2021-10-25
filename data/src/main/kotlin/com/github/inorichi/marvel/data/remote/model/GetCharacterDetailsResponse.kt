package com.github.inorichi.marvel.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GetCharacterDetailsResponse(
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
    val description: String,
    val thumbnail: Thumbnail,
    val comics: ContentItems<Comic>,
    val series: ContentItems<Series>,
    val urls: List<Url>
  )

  @Serializable
  data class Thumbnail(
    val path: String,
    val extension: String
  )

  @Serializable
  data class ContentItems<T>(
    val items: List<T>
  )

  @Serializable
  data class Comic(
    val name: String,
    val resourceURI: String
  )

  @Serializable
  data class Series(
    val name: String,
    val resourceURI: String
  )

  @Serializable
  data class Url(
    val type: String,
    val url: String
  )

}
