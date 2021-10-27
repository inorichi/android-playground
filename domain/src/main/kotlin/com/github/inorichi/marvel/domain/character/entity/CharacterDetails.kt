package com.github.inorichi.marvel.domain.character.entity

/**
 * The details of a Marvel character.
 */
data class CharacterDetails(
  val id: Int,
  val name: String,
  val description: String,
  val thumbnail: String,
  val comics: List<CharacterComic>,
  val series: List<CharacterSeries>
)
