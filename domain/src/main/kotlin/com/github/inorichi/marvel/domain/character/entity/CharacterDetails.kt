package com.github.inorichi.marvel.domain.character.entity

data class CharacterDetails(
  val id: Int,
  val name: String,
  val thumbnail: String,
  val wikiUrl: String?,
  val comics: List<Comic>,
  val series: List<Series>
)

data class Comic(
  val name: String,
  val resourceUrl: String
)

data class Series(
  val name: String,
  val resourceUrl: String
)
