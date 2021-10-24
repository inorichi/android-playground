package com.github.inorichi.marvel.data.features.character.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
  @PrimaryKey val id: Int,
  val name: String,
  val thumbnail: String
)
