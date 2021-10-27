package com.github.inorichi.marvel.domain.character.entity

/**
 * The overview of a Marvel character.
 */
data class CharacterOverview(
  val id: Int,
  val name: String,
  val thumbnail: String
)
