package com.github.inorichi.marvel.data.features.character.mapper

import com.github.inorichi.marvel.data.remote.model.GetCharacterDetailsResponse
import com.github.inorichi.marvel.domain.character.entity.CharacterComic
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterSeries

/**
 * Converts a [GetCharacterDetailsResponse] API response to a domain entity.
 */
fun GetCharacterDetailsResponse.toEntity(): CharacterDetails? {
  return data.results.firstOrNull()?.toEntity()
}

/**
 * Converts a [GetCharacterDetailsResponse.Result] to a domain entity.
 */
private fun GetCharacterDetailsResponse.Result.toEntity(): CharacterDetails {
  return CharacterDetails(
    id = id,
    name = name,
    description = description,
    thumbnail = "${thumbnail.path}/portrait_xlarge.${thumbnail.extension}",
    series = series.items.map { it.toEntity() },
    comics = comics.items.map { it.toEntity() },
  )
}

/**
 * Converts a [GetCharacterDetailsResponse.Series] to a domain entity.
 */
private fun GetCharacterDetailsResponse.Series.toEntity(): CharacterSeries {
  return CharacterSeries(
    name = name,
    url = resourceURI
  )
}

/**
 * Converts a [GetCharacterDetailsResponse.Comic] to a domain entity.
 */
private fun GetCharacterDetailsResponse.Comic.toEntity(): CharacterComic {
  return CharacterComic(
    name = name,
    url = resourceURI
  )
}
