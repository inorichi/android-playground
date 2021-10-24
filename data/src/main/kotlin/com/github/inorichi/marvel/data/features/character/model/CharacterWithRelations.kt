package com.github.inorichi.marvel.data.features.character.model

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithRelations(
  @Embedded val character: Character,
  @Relation(parentColumn = "id", entityColumn = "characterId")
  val comics: List<CharacterComic> = emptyList(),
  @Relation(parentColumn = "id", entityColumn = "characterId")
  val series: List<CharacterSeries> = emptyList()
)
