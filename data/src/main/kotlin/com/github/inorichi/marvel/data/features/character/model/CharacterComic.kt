package com.github.inorichi.marvel.data.features.character.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
  foreignKeys = [
    ForeignKey(
      entity = Character::class,
      parentColumns = arrayOf("id"),
      childColumns = arrayOf("characterId"),
      onDelete = ForeignKey.CASCADE
    )
  ],
  primaryKeys = ["characterId", "name"]
)
data class CharacterComic(
  val characterId: Int,
  val name: String,
  val resourceUrl: String
)
