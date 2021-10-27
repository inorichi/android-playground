package com.github.inorichi.marvel.data.features.character.mapper

import com.github.inorichi.marvel.data.features.character.model.Character
import com.github.inorichi.marvel.data.features.character.model.CharacterWithRelations
import com.github.inorichi.marvel.domain.character.entity.CharacterComic
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterSeries
import com.github.inorichi.marvel.data.features.character.model.CharacterComic as CharacterComicDb
import com.github.inorichi.marvel.data.features.character.model.CharacterSeries as CharacterSeriesDb

/**
 * Converts the [CharacterDetails] entity to a database model.
 */
fun CharacterDetails.toDbModel(): Character {
  return Character(
    id = id,
    name = name,
    thumbnail = thumbnail,
    description = description
  )
}

/**
 * Converts the [CharacterSeries] entity to a database model
 */
fun CharacterSeries.toDbModel(characterId: Int): CharacterSeriesDb {
  return CharacterSeriesDb(
    characterId = characterId,
    name = name,
    url = url
  )
}

/**
 * Converts the [CharacterComic] entity to a database model.
 */
fun CharacterComic.toDbModel(characterId: Int): CharacterComicDb {
  return CharacterComicDb(
    characterId = characterId,
    name = name,
    url = url
  )
}

/**
 * Converts the [CharacterWithRelations] model to a domain entity.
 */
fun CharacterWithRelations.toEntity(): CharacterDetails {
  return CharacterDetails(
    id = character.id,
    name = character.name,
    thumbnail = character.thumbnail,
    description = character.description,
    comics = comics.map { it.toEntity() },
    series = series.map { it.toEntity() }
  )
}

/**
 * Converts the [CharacterComicDb] model to a domain entity.
 */
private fun CharacterComicDb.toEntity(): CharacterComic {
  return CharacterComic(
    name = name,
    url = url
  )
}

/**
 * Converts the [CharacterSeriesDb] model to a domain entity.
 */
private fun CharacterSeriesDb.toEntity(): CharacterSeries {
  return CharacterSeries(
    name = name,
    url = url
  )
}
