package com.github.inorichi.marvel.data.features.character.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * A Room composed model to retrieve a [character] and its [comics] and [series].
 */
data class CharacterWithRelations(
  @Embedded val character: Character,
  @Relation(parentColumn = "id", entityColumn = "characterId")
  val comics: List<CharacterComic> = emptyList(),
  @Relation(parentColumn = "id", entityColumn = "characterId")
  val series: List<CharacterSeries> = emptyList()
)
