package com.github.inorichi.marvel.data.features.character.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A Room entity for storing a [Character].
 */
@Entity
data class Character(
  @PrimaryKey val id: Int,
  val name: String,
  val thumbnail: String,

  @ColumnInfo(defaultValue = "")
  val description: String = ""
)
